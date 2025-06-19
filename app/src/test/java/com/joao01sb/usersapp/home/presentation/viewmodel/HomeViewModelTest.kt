package com.joao01sb.usersapp.home.presentation.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForDto
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocal
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemote
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.SaveUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

//class HomeViewModelTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var loadAndSyncUsers: LoadAndSyncUsers
//    private lateinit var scheduleRemoteSync: ScheduleRemoteSync
//
//    @Before
//    fun setUp() {
//        mockkStatic(Log::class)
//        every { Log.d(any(), any()) } returns 0
//        every { Log.e(any(), any()) } returns 0
//        every { Log.e(any(), any<String>(), any()) } returns 0
//        scheduleRemoteSync = mockk(relaxed = true)
//        loadAndSyncUsers = mockk(relaxed = true)
//        viewModel = HomeViewModel(loadAndSyncUsers, scheduleRemoteSync)
//    }
//
//    @After
//    fun tearDown() {
//        unmockkStatic(Log::class)
//    }
//
//    @Test
//    fun `when search user loading is true`() = runTest{
//        val localUsers = MockUserFactory.createUserListEntity().toModelForEntenty()
//
//        val remoteUsers = ResultWrapper.Success(MockUserFactory.createUserListDto().toModelForDto())
//
//        coEvery { getLocalUsersUseCase.invoke() } returns flowOf(localUsers)
//
//        coEvery { getRemoteUsersUseCase.invoke() } returns flowOf(remoteUsers)
//
//        viewModel.loadInitialUsers()
//
//        assertTrue(viewModel.uiState.value.isLoading)
//    }
//
//    @Test
//    fun `when init app search local users success`() = runTest {
//        val localUsers = MockUserFactory.createUserListEntity().toModelForEntenty()
//
//        coEvery { getLocalUsersUseCase.invoke() } returns flowOf(localUsers)
//
//        viewModel.loadInitialUsers()
//
//        viewModel.uiState.test {
//
//            assertEquals(localUsers, awaitItem().users)
//
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `when local users is empty, seach remote users success`() = runTest {
//        val localUsers = emptyList<UserEntity>().toModelForEntenty()
//        val remoteUsers = ResultWrapper.Success(MockUserFactory.createUserListDto().toModelForDto())
//
//        coEvery { getLocalUsersUseCase.invoke() } returns flowOf(localUsers)
//
//        coEvery { getRemoteUsersUseCase.invoke() } returns flowOf(remoteUsers)
//
//        coEvery { saveUsers.invoke(any()) } returns Unit
//
//        viewModel.loadInitialUsers()
//
//        viewModel.uiState.test {
//
//            assertTrue(awaitItem().isLoading)
//
//            assertEquals(remoteUsers.result, awaitItem().users)
//
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//}