package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.MockUserFactory.toModelForEntenty
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocal
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.sql.SQLDataException
import kotlin.test.assertFailsWith


class GetUsersLocalImpTest {

    lateinit var repository: UserLocalRepository
    lateinit var useCase: GetUsersLocal

    @Before
    fun setup() {
        repository = mockk<UserLocalRepository>(relaxed = true)
        useCase = GetUsersLocalImp(repository)
    }

    @Test
    fun `invoke returns list of users when repository is not empty`() = runTest {

        val users = MockUserFactory.createUserListEntity()

        coEvery { repository.getUsersLocal() } returns flowOf(users)

        val localUsers = useCase.invoke().first()

        assertEquals(localUsers, users.toModelForEntenty())

    }

    @Test
    fun `invoke returns empty list when repository is empty`() = runTest {

        val users = listOf<UserEntity>()

        coEvery { repository.getUsersLocal() } returns flowOf(users)

        val localUsers = useCase.invoke().first()

        assertEquals(localUsers, users.toModelForEntenty())
    }

    @Test
    fun `invoke returns throw when repository throws`() = runTest {
        coEvery { repository.getUsersLocal() } throws SQLDataException()

        assertFailsWith<SQLDataException> {
            useCase.invoke().first()
        }
    }

}
