package com.joao01sb.usersapp.home.data.usecase

import app.cash.turbine.test
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForDto
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocal
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemote
import com.joao01sb.usersapp.home.domain.usecase.LoadAndSyncUsers
import com.joao01sb.usersapp.home.domain.usecase.SaveUsers
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoadAndSyncUsersImpTest {

    lateinit var loadAndSyncUsers: LoadAndSyncUsers
    lateinit var getRemoteUsers: GetUsersRemote
    lateinit var getLocalUsers: GetUsersLocal
    lateinit var saveUsers: SaveUsers

    @Before
    fun setup() {
        getRemoteUsers = mockk<GetUsersRemote>(relaxed = true)
        getLocalUsers = mockk<GetUsersLocal>(relaxed = true)
        saveUsers = mockk<SaveUsers>(relaxed = true)
        loadAndSyncUsers = LoadAndSyncUsersImp(getRemoteUsers, getLocalUsers, saveUsers)
    }

    @Test
    fun `verify that invoke calls getLocalUsers`() = runTest {
        coEvery { getLocalUsers.invoke() } returns flowOf(MockUserFactory.createUserListEntity().toModelForEntenty())

        loadAndSyncUsers.invoke().first()

        coVerify(exactly = 1) { getLocalUsers.invoke() }
    }

    @Test
    fun `verify that invoke calls getRemoteUsers given getLocalUsers returns empty list`()  = runTest{
        coEvery { getLocalUsers.invoke() } returns flowOf(emptyList())

        coEvery { getRemoteUsers.invoke() } returns flowOf(ResultWrapper.Success(MockUserFactory.createUserListDto().toModelForDto()))

        loadAndSyncUsers.invoke().first()

        coVerify(exactly = 1) { getRemoteUsers.invoke() }
    }

    @Test
    fun `given getRemoteUsers returns success, verify that invoke returns success`() = runTest {
        coEvery { getLocalUsers.invoke() } returns flowOf(emptyList())

        coEvery { getRemoteUsers.invoke() } returns flowOf(ResultWrapper.Success(MockUserFactory.createUserListDto().toModelForDto()))

        coEvery { saveUsers.invoke(any()) } returns Unit

        loadAndSyncUsers.invoke().test {
            val result = awaitItem()

            assert(result is ResultWrapper.Success)

            awaitComplete()
        }
    }

    @Test
    fun `given getRemoteUsers returns success, verify that invoke calls saveUsers`() = runTest {
        coEvery { getLocalUsers.invoke() } returns flowOf(emptyList())

        coEvery { getRemoteUsers.invoke() } returns flowOf(ResultWrapper.Success(MockUserFactory.createUserListDto().toModelForDto()))

        coEvery { saveUsers.invoke(any()) } returns Unit

        loadAndSyncUsers.invoke().first()

        coVerify(exactly = 1) { saveUsers.invoke(any()) }
    }

    @Test
    fun `given getRemoteUsers returns error, verify that invoke returns error`() = runTest{
        val error = Exception("Error syncing users")

        coEvery { getLocalUsers.invoke() } returns flowOf(emptyList())

        coEvery { getRemoteUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

        coEvery { saveUsers.invoke(any()) } returns Unit

        loadAndSyncUsers.invoke().test {
            val result = awaitItem()

            assert(result is ResultWrapper.Error)

            awaitComplete()
        }
    }

    @Test
    fun `given syncUsers returns success, verify that invoke returns success`() = runTest {
        coEvery { getRemoteUsers.invoke() } returns flowOf(ResultWrapper.Success(MockUserFactory.createUserListDto().toModelForDto()))

        coEvery { saveUsers.invoke(any()) } returns Unit

        loadAndSyncUsers.syncUsers().test {
            val result = awaitItem()

            assert(result is ResultWrapper.Success)

            awaitComplete()
        }
    }

    @Test
    fun `given syncUsers returns error, verify that invoke returns error`() = runTest {
        val error = Exception("Error syncing users")

        coEvery { getRemoteUsers.invoke() } returns flowOf(ResultWrapper.Error(error))

        loadAndSyncUsers.syncUsers().test {
            val result = awaitItem()

            assert(result is ResultWrapper.Error)

            awaitComplete()
        }
    }

}