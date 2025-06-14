package com.joao01sb.usersapp.home.data.repository

import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRemoteRepositoryImpTest {

    lateinit var remoteDataSource: UserRemoteDataSource
    lateinit var repository: UserRemoteRepository

    @Before
    fun setup() {
        remoteDataSource = mockk<UserRemoteDataSource>(relaxed = true)
        repository = UserRemoteRepositoryImp(remoteDataSource)
    }

    @Test
    fun `invoke returns list of users when repository is not empty`() = runTest {
        val users = MockUserFactory.createUserListDto()
        val result = ResultWrapper.Success(users)

        coEvery { remoteDataSource.getUsers() } returns flowOf(result)

        repository.getUsersRemote().test {
            assertEquals(awaitItem(), result)
        }
    }

    @Test
    fun `invoke returns empty list when repository is empty`() = runTest {
        val result = ResultWrapper.Success(listOf<UserDto>())

        coEvery { remoteDataSource.getUsers() } returns flowOf(result)

        val userList = repository.getUsersRemote().first()

        assertEquals(userList, result)
    }

    @Test
    fun `invoke returns throw when repository throws`() = runTest {
        val result = ResultWrapper.Error(Exception())

        coEvery { remoteDataSource.getUsers() } returns flowOf(result)

        repository.getUsersRemote().test {
            assertEquals(awaitItem(), result)

            awaitComplete()
        }
    }

}