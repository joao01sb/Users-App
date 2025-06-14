package com.joao01sb.usersapp.home.data.repository

import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.home.data.datasource.UserLocalDataSourceImp
import com.joao01sb.usersapp.home.domain.datasource.UserLocalDataSource
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.sql.SQLDataException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserLocalRepositoryImpTest {

    lateinit var dataSource: UserLocalDataSource
    lateinit var repository: UserLocalRepository

    @Before
    fun setup() {
        dataSource = UserLocalDataSourceImp(dao = mockk<UserDao>(relaxed = true))
        repository = UserLocalRepositoryImp(dataSource)
    }

    @Test
    fun `given returns list of users when repository is not empty`() = runTest {
        val usersEntities = MockUserFactory.createUserListEntity()

        coEvery { dataSource.getUsers() } returns flowOf(usersEntities)

        repository.getUsersLocal().test {
            assertEquals(awaitItem(), usersEntities)

            awaitComplete()
        }
    }

    @Test
    fun `given empty list when repository is empty`() = runTest{
        coEvery { dataSource.getUsers() } returns flowOf(listOf())

        val userList = repository.getUsersLocal().first()

        assertEquals(userList, listOf())
    }

    @Test
    fun `given returns throw when repository throws`() = runTest {
        coEvery { dataSource.getUsers() } throws SQLDataException()

        assertFailsWith<SQLDataException> {
            repository.getUsersLocal().first()
        }

    }

}