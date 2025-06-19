package com.joao01sb.usersapp.home.data.usecase

import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForDto
import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class GetUsersRemoteImpTest {

    lateinit var repository: UserRemoteRepository
    lateinit var useCase: GetUsersRemote

    @Before
    fun setup() {
        repository = mockk<UserRemoteRepository>(relaxed = true)
        useCase = GetUsersRemoteImp(repository)
    }

    @Test
    fun `invoke returns list of users mapper when repository is not empty`() = runTest {
        val users = MockUserFactory.createUserListDto()

        coEvery { repository.getUsersRemote() } returns flowOf(ResultWrapper.Success(users))

        useCase.invoke().test {

            val loading = awaitItem()

            assertEquals(loading, ResultWrapper.Loading)

            assertEquals(awaitItem(), ResultWrapper.Success(users.toModelForDto()))

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty users when repository is empty`() = runTest{
        val users = listOf<UserDto>()

        coEvery { repository.getUsersRemote() } returns flowOf(ResultWrapper.Success(users))

        useCase.invoke().test {
            val loading = awaitItem()

            assertEquals(loading, ResultWrapper.Loading)

            val result = awaitItem()

            assert(result is ResultWrapper.Error)

            assertEquals((result as ResultWrapper.Error).error.message, "empty users")

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns throw when repository throws`() = runTest {

        coEvery { repository.getUsersRemote() } returns flowOf(ResultWrapper.Error(Exception()))

        useCase.invoke().test {

            val loading = awaitItem()

            assertEquals(loading, ResultWrapper.Loading)

            val result = awaitItem()

            assert(result is ResultWrapper.Error)

            awaitComplete()
        }
    }

}