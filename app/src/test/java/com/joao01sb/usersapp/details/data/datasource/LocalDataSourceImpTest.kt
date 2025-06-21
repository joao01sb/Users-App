package com.joao01sb.usersapp.details.data.datasource

import androidx.sqlite.SQLiteException
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.details.domain.datasource.LocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import java.sql.SQLDataException
import kotlin.test.assertFailsWith

class LocalDataSourceImpTest {

    lateinit var userDao: UserDao
    lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        userDao = mockk<UserDao>()
        localDataSource = LocalDataSourceImp(userDao)
    }

    @Test
    fun `when searching for data in the database it returns users`() = runTest {
        val localUsersMock = MockUserFactory.createUserListEntity().firstOrNull()

        coEvery { userDao.getUserById(any()) } returns localUsersMock

        val resultSearchUser = localDataSource.getUserById(0)

        assertEquals(resultSearchUser, localUsersMock)

        coVerify { userDao.getUserById(any()) }
    }

    @Test
    fun `when searching for data in the database it returns empty user`() = runTest {
        coEvery { userDao.getUserById(any()) } returns null

        val resultSearchUser = localDataSource.getUserById(0)

        assertEquals(resultSearchUser, null)

        coVerify { userDao.getUserById(any()) }
    }

    @Test
    fun `when searching for data in the database it returns error`() = runTest {
        val sqliteException = SQLiteException("Column not found")

        coEvery { userDao.getUserById(any()) } throws sqliteException

        assertFailsWith<SQLiteException> {
            localDataSource.getUserById(0)
        }

        coVerify { userDao.getUserById(any()) }
    }

}