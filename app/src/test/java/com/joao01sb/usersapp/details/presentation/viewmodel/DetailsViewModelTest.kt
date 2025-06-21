package com.joao01sb.usersapp.details.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.details.domain.usecase.GetUserById
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var getUserById: GetUserById
    lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setup() {
        getUserById = mockk<GetUserById>()
        detailsViewModel = DetailsViewModel(1,getUserById)
    }

    @Test
    fun `when calling getUser returns loading in uiState`() = runTest {
        detailsViewModel.getUser()

        detailsViewModel.stateDetails.test {
            assertEquals(awaitItem().result,  ResultWrapper.Loading)

            awaitComplete()
        }
    }

    @Test
    fun `when userId is valid, it should issue Success with the correct user`() = runTest {
        val userLocal = MockUserFactory.createUserListEntity().first().toModel()

        coEvery { getUserById.invoke(any()) } returns Result.success(userLocal)

        detailsViewModel.stateDetails.test {
            detailsViewModel.getUser()

            assertEquals(awaitItem().result, ResultWrapper.Loading)

            assertEquals(awaitItem().result, ResultWrapper.Success(userLocal))
        }
    }

    @Test
    fun `when userId is not present, it should throw Error`() = runTest {
        val exception = Exception("user empty")
        val userNotFound = Result.failure<User>(exception)

        coEvery { getUserById.invoke(any()) } returns userNotFound

        detailsViewModel.stateDetails.test {
            detailsViewModel.getUser()

            assertEquals(awaitItem().result, ResultWrapper.Loading)

            assertEquals(awaitItem().result, ResultWrapper.Error(exception))
        }
    }

  
}