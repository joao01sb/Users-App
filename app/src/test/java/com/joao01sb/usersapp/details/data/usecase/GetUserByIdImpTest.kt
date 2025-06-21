package com.joao01sb.usersapp.details.data.usecase

import androidx.sqlite.SQLiteException
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.details.data.repository.UserDetailsRepositoryImp
import com.joao01sb.usersapp.details.data.repository.UserDetailsRepositoryImpTest
import com.joao01sb.usersapp.details.domain.repository.UserDetailsRepository
import com.joao01sb.usersapp.details.domain.usecase.GetUserById
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserByIdImpTest {

    lateinit var userDetailsRepository: UserDetailsRepository
    lateinit var getUserById: GetUserById

    @Before
    fun setup() {
        userDetailsRepository = mockk<UserDetailsRepositoryImp>()
        getUserById = GetUserByIdImp(userDetailsRepository)
    }

    @Test
    fun `when invoking local user search it returns user found successfully`() = runTest{
        val userLocalMock = MockUserFactory.createUserListEntity().firstOrNull()

        coEvery { userDetailsRepository.getUserById(any()) } returns userLocalMock

        val userLocal = getUserById.invoke(0)

        assert(userLocal.isSuccess)

        assertEquals(userLocal.getOrNull(), userLocalMock?.toModel())
    }

    @Test
    fun `when you invoke the local user search it returns and if it is not found it returns an error`() = runTest{
        coEvery { userDetailsRepository.getUserById(any()) } returns null

        val userLocal = getUserById.invoke(0)

        assert(userLocal.isFailure)

        assertEquals(userLocal.exceptionOrNull()?.message, "User not found")
    }

    @Test
    fun `when invoking local user search returns exception returns failure`() = runTest{
        val exception = SQLiteException("sintax")
        coEvery { userDetailsRepository.getUserById(any()) } throws exception

        val userLocal = getUserById.invoke(0)

        assert(userLocal.isFailure)

        assertEquals(userLocal.exceptionOrNull(), exception)
    }

}