package com.joao01sb.usersapp.home.data.usecase

import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForDto
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.ScheduleRemoteSync
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ScheduleRemoteSyncImpTest {

    lateinit var scheduleRemoteSync: ScheduleRemoteSync
    lateinit var loadAndSyncUsers: LoadAndSyncUsers

    @Before
    fun setup() {
        loadAndSyncUsers = mockk<LoadAndSyncUsers>(relaxed = true)
        scheduleRemoteSync = ScheduleRemoteSyncImp(loadAndSyncUsers)
    }

    @Test
    fun `verify that execute calls syncUsers`() = runTest {
        coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(emptyList()))

        scheduleRemoteSync.execute(0L).first()

        coVerify(exactly = 1) { loadAndSyncUsers.syncUsers() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify that execute calls syncUsers with delay`() = runTest {
        coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(emptyList()))

        val delayMillis = 5000L

        scheduleRemoteSync.execute(delayMillis).first()

        advanceTimeBy(delayMillis)

        coVerify(exactly = 1, timeout = delayMillis) { loadAndSyncUsers.syncUsers() }
    }

    @Test
    fun `verify that execute returns syncUsers result`() = runTest {
        val users = MockUserFactory.createUserListDto().toModelForDto()

        coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Success(users))

        val result = scheduleRemoteSync.execute().first()

        assertEquals(result, ResultWrapper.Success(users))
    }

    @Test
    fun `verify that execute returns syncUsers error`() = runTest {
        val error = Exception("Error syncing users")

        coEvery { loadAndSyncUsers.syncUsers() } returns flowOf(ResultWrapper.Error(error))

        val result = scheduleRemoteSync.execute().first()

        assertEquals(result, ResultWrapper.Error(error))

    }

}
