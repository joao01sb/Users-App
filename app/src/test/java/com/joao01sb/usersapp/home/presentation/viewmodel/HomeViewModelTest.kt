package com.joao01sb.usersapp.home.presentation.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.core.utils.TIME_FOR_SYNC_USERS
import com.joao01sb.usersapp.core.utils.UiEvent
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var loadAndSyncUsers: LoadAndSyncUsers
    private lateinit var scheduleRemoteSync: ScheduleRemoteSync
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

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
        testDispatcher.scheduler.advanceUntilIdle()
        Dispatchers.resetMain()
        clearAllMocks()
        unmockkStatic(Log::class)
    }

    @Test
    fun `loadUsers should update uiState with users when use case returns success`() =
        runTest(testDispatcher) {
            val users = MockUserFactory.createUserListEntity().toModelForEntenty()
            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Success(users))

            viewModel.loadUsers()
            advanceUntilIdle()

            coVerify(exactly = 2) { loadAndSyncUsers.invoke() }

            viewModel.uiState.test {
                val item = awaitItem()
                assertEquals(users, item.users)
                assertFalse(item.isLoading)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `loadUsers should update uiState with error when use case returns error`() =
        runTest(testDispatcher) {
            val errorMessage = "test error"
            val error = Exception(errorMessage)
            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

            viewModel.loadUsers()
            advanceUntilIdle()

            viewModel.uiState.test {
                val item = awaitItem()

                assertNotNull(item.isErro)
                assertTrue(item.isErro?.first == true)
                assertEquals(errorMessage, item.isErro?.second)
                assertFalse(item.isLoading)

                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 2) { loadAndSyncUsers.invoke() }
        }

    @Test
    fun `loadUsers should show loading state when use case returns loading`() =
        runTest(testDispatcher) {
            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Loading)

            viewModel.loadUsers()
            advanceUntilIdle()

            viewModel.uiState.test {
                val item = awaitItem()
                assertTrue(item.isLoading)
                assertTrue(item.users.isEmpty())
                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 2) { loadAndSyncUsers.invoke() }
        }

    @Test
    fun `refresh should call syncUsers and show loading state`() =
        runTest(testDispatcher) {
            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Loading)

            viewModel.refresh()
            advanceUntilIdle()

            viewModel.uiState.test {
                val item = awaitItem()
                assertTrue(item.isLoading)

                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 1) { loadAndSyncUsers.syncUsers() }
        }

    @Test
    fun `refresh should update uiState with users when syncUsers returns success`() =
        runTest(testDispatcher) {
            val users = MockUserFactory.createUserListEntity().toModelForEntenty()
            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(users))

            viewModel.refresh()
            advanceUntilIdle()

            viewModel.uiState.test {
                val item = awaitItem()
                assertEquals(users, item.users)
                assertFalse(item.isLoading)
                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 1) { loadAndSyncUsers.syncUsers() }
        }

    @Test
    fun `refresh should update uiState with error when syncUsers returns error`() =
        runTest(testDispatcher) {
            val errorMessage = "test error"
            val error = Exception(errorMessage)
            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Error(error))

            viewModel.refresh()
            advanceUntilIdle()

            viewModel.uiState.test {
                val item = awaitItem()

                assertNotNull(item.isErro)
                assertTrue(item.isErro?.first == true)
                assertEquals(errorMessage, item.isErro?.second)
                assertFalse(item.isLoading)

                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 1) { loadAndSyncUsers.syncUsers() }
        }

    @Test
    fun `refresh should show loading state when syncUsers returns loading`() =
        runTest(testDispatcher) {
            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Loading)

            viewModel.refresh()
            advanceUntilIdle()

            viewModel.uiState.test {
                val item = awaitItem()
                assertTrue(item.isLoading)

                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 1) { loadAndSyncUsers.syncUsers() }
        }

    @Test
    fun `syncUsers should show success toast when operation succeeds`() =
        runTest(testDispatcher) {
            val users = MockUserFactory.createUserListEntity().toModelForEntenty()
            coEvery { scheduleRemoteSync.execute(TIME_FOR_SYNC_USERS) } returns flowOf(ResultWrapper.Success(users))

            viewModel.uiEvents.test {
                viewModel.syncUsers()
                advanceTimeBy(TIME_FOR_SYNC_USERS)

                val item = awaitItem()
                assertTrue(item is UiEvent.ShowToast)
                assertEquals("Users updated", (item as UiEvent.ShowToast).message)

                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 2) { scheduleRemoteSync.execute(TIME_FOR_SYNC_USERS) }
        }

    @Test
    fun `syncUsers should show error toast when operation fails`() =
        runTest(testDispatcher) {
            val errorMessage = "test error"
            val error = Exception(errorMessage)
            coEvery { loadAndSyncUsers.invoke() } returns flowOf(ResultWrapper.Success(emptyList()))
            coEvery { scheduleRemoteSync.execute(any()) } returns flowOf(ResultWrapper.Error(error))
            coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(emptyList()))

            viewModel.uiEvents.test {
                viewModel.syncUsers()
                advanceTimeBy(TIME_FOR_SYNC_USERS)

                val item = awaitItem()

                assertTrue(item is UiEvent.ShowToast)
                assertEquals(errorMessage, (item as UiEvent.ShowToast).message)

                cancelAndConsumeRemainingEvents()
            }

            coVerify(exactly = 2) { scheduleRemoteSync.execute(TIME_FOR_SYNC_USERS) }
        }
}
