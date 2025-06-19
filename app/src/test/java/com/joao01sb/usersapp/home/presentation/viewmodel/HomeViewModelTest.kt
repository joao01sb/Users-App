package com.joao01sb.usersapp.home.presentation.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var loadAndSyncUsers: LoadAndSyncUsers
    private lateinit var scheduleRemoteSync: ScheduleRemoteSync

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any<String>(), any()) } returns 0
        scheduleRemoteSync = mockk(relaxed = true)
        loadAndSyncUsers = mockk(relaxed = true)
        viewModel = HomeViewModel(loadAndSyncUsers, scheduleRemoteSync)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `given loadAndSyncUsers returns success, when loadUsers is called, then uiState is updated with users`() =
        runTest {
            val users = MockUserFactory.createUserListEntity().toModelForEntenty()

            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Success(users))

            viewModel.loadUsers()

            coVerify { loadAndSyncUsers.invoke() }

            viewModel.uiState.test {
                val item = awaitItem()

                assertEquals(item.users, users)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `given loadAndSyncUsers returns error, when loadUsers is called, then uiState is updated with error message`() =
        runTest {
            val errorMessage = "test error"
            val error = Exception(errorMessage)

            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

            viewModel.loadUsers()

            coVerify { loadAndSyncUsers.invoke() }

            viewModel.uiState.test {
                val item = awaitItem()

                assertTrue(item.isErro?.first == true)

                assertEquals(item.isErro?.second, errorMessage)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `given loadAndSyncUsers returns loading, when loadUsers is called, then uiState is updated with loading state`() =
        runTest {
            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Loading)

            viewModel.loadUsers()

            coVerify { loadAndSyncUsers.invoke() }

            viewModel.uiState.test {
                val item = awaitItem()

                assertTrue(item.isLoading)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `when refresh is called, then loadAndSyncUsers is called`() = runTest {
        viewModel.refresh()

        coVerify { loadAndSyncUsers.syncUsers() }

        viewModel.uiState.test {
            val item = awaitItem()

            assertTrue(item.isLoading)

            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `when refresh is called and loadAndSyncUsers returns success, then uiState is updated`() =
        runTest {
            val users = MockUserFactory.createUserListEntity().toModelForEntenty()

            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(users))

            viewModel.refresh()

            viewModel.uiState.test {
                val item = awaitItem()

                assertEquals(item.users, users)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `when refresh is called and loadAndSyncUsers returns error, then uiState is updated`() =
        runTest {
            val errorMessage = "test error"
            val error = Exception(errorMessage)

            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Error(error))

            viewModel.refresh()

            viewModel.uiState.test {
                val item = awaitItem()

                assertTrue(item.isErro?.first == true)

                assertEquals(item.isErro?.second, errorMessage)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `when refresh is called and loadAndSyncUsers returns loading, then uiState is updated`() =
        runTest {
            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Loading)

            viewModel.refresh()

            viewModel.uiState.test {
                val item = awaitItem()

                assertTrue(item.isLoading)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `when invoke success, then uiEvents is updated`() = runTest {
        val duration = 60000L
        val users = MockUserFactory.createUserListEntity().toModelForEntenty()

        coEvery { scheduleRemoteSync.execute(duration) } returns flowOf(ResultWrapper.Success(users))

        viewModel.syncUsers()

        advanceTimeBy(duration)

        viewModel.uiEvents.test {
            val item = awaitItem()

            assertTrue(item is UiEvent.ShowToast)

            cancelAndConsumeRemainingEvents()
        }

    }


    @Test
    fun `when loadUsers is called and loadAndSyncUsers returns error, then uiEvents is updated`() = runTest {
        val errorMessage = "test error"
        val error = Exception(errorMessage)

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

       viewModel.uiState.test {
           viewModel.loadUsers()

           assert(awaitItem().users.isEmpty())

           val item = awaitItem()

           assertTrue(item.isErro?.first == true)

           cancelAndConsumeRemainingEvents()
       }

    }

    @Test
    fun `when loadUsers is called and loadAndSyncUsers returns loading, then uiEvents is updated`() = runTest {
        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Loading)

        viewModel.loadUsers()

        viewModel.uiState.test {
            val item = awaitItem()

            assertTrue(item.isLoading)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when syncUsers is called and scheduleRemoteSync returns success, then uiEvents is updated`() = runTest {
        val users = MockUserFactory.createUserListEntity().toModelForEntenty()

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Success(users))

        viewModel.loadUsers()

        viewModel.uiState.test {
            val item = awaitItem()

            assertEquals(item.users, users)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when syncUsers is called and scheduleRemoteSync returns error, then uiEvents is updated`() = runTest {
        val errorMessage = "test error"
        val error = Exception(errorMessage)

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

        viewModel.loadUsers()

        viewModel.uiState.test {
            val item = awaitItem()

            assertTrue(item.isErro?.first == true)

            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `when syncUsers is called and scheduleRemoteSync returns loading, then uiState is updated`() = runTest {

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Loading)

        viewModel.loadUsers()

        viewModel.uiState.test {
            val item = awaitItem()

            assertTrue(item.isLoading)

            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `when syncUsers is called and scheduleRemoteSync returns error then uiState is updated`() = runTest {
        val errorMessage = "test error"
        val error = Exception(errorMessage)

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

        viewModel.loadUsers()

        viewModel.uiState.test {
            val item = awaitItem()

            assertTrue(item.isErro?.first == true)

            cancelAndConsumeRemainingEvents()
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when syncUser is called and scheduleRemoteSync returns error then uiEvents is update toast`() = runTest {
        val errorMessage = "test error"
        val error = Exception(errorMessage)

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Success(emptyList()))

        coEvery { scheduleRemoteSync.execute(any()) } returns flowOf(ResultWrapper.Error(error))

        coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(emptyList()))

        viewModel.uiEvents.test {
            viewModel.syncUsers()

            advanceTimeBy(60000L)

            val item = awaitItem()

            assertTrue(item is UiEvent.ShowToast)
            assertEquals(errorMessage, (item as UiEvent.ShowToast).message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when syncUser is called and scheduleRemoteSync returns success then uiEvents is update toast`() = runTest {
        val users = MockUserFactory.createUserListEntity().toModelForEntenty()

        coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Success(emptyList()))

        coEvery { scheduleRemoteSync.execute(any()) } returns flowOf(ResultWrapper.Success(users))

        viewModel.uiEvents.test {
            viewModel.syncUsers()

            advanceTimeBy(60000L)

            val item = awaitItem()

            assertTrue(item is UiEvent.ShowToast)

            assertEquals("Users updated", (item as UiEvent.ShowToast).message)

            cancelAndConsumeRemainingEvents()
        }
    }


}