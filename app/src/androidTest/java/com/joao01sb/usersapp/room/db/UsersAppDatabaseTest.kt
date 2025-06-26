package com.joao01sb.usersapp.room.db

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.usersapp.core.data.local.AppDatabase
import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.home.presentation.fake.MockUserFactory
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.Test


@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class UsersAppDatabaseTest {

    lateinit var db: AppDatabase
    lateinit var dao: UserDao

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.usersDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        db.close()
        testDispatcher.scheduler.advanceUntilIdle()
        Dispatchers.resetMain()
    }

    @Test
    fun verify_insert_users() = runTest(testDispatcher) {
        val users = MockUserFactory.createUserListEntity()

        dao.insertUser(users)

        val usersSave = dao.allUsers().first()

        assertThat(usersSave).hasSize(users.size)
    }


    @Test
    fun verify_insert_users_and_find_user_by_id_success() = runTest(testDispatcher) {
        val usersInsert = MockUserFactory.createUserListEntity()

        dao.insertUser(usersInsert)

        val userFindById = dao.getUserById(usersInsert.get(1).id)

        assertThat(userFindById).isIn(usersInsert)
    }

    @Test
    fun verify_when_find_user_by_id_isNotFound() = runTest(testDispatcher) {
        val usersInsert = MockUserFactory.createUserListEntity().first()

        dao.insertUser(listOf(usersInsert))

        dao.deleteUserById(usersInsert.id)

        val userFindById = dao.getUserById(usersInsert.id)

        assertThat(userFindById).isNull()
    }

    @Test
    fun delete_user_from_table() = runTest(testDispatcher) {
        val users = MockUserFactory.createUserListEntity()

        dao.insertUser(users)

        dao.deleteUserById(users.first().id)

        val usersDatabase = dao.allUsers().first()

        assertThat(usersDatabase).doesNotContain(users.first())

        assertThat(usersDatabase).containsExactly(users.get(1))

        assertThat(usersDatabase).hasSize(1)

    }


}