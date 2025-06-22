package com.joao01sb.usersapp.details.data.repository

import androidx.sqlite.SQLiteException
import com.joao01sb.usersapp.MockUserFactory
import com.joao01sb.usersapp.details.domain.datasource.LocalDataSource
import com.joao01sb.usersapp.details.domain.repository.UserDetailsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class UserDetailsRepositoryImpTest {

    lateinit var databaseSource: LocalDataSource
    lateinit var userDetailsRepository: UserDetailsRepository

    @Before
    fun setup() {
        databaseSource = mockk<LocalDataSource>()
        userDetailsRepository = UserDetailsRepositoryImp(databaseSource)
    }

    @Test
    fun `when fetching local user returns user successfully`() = runTest {
        val userLocal = MockUserFactory.createUserListEntity().firstOrNull()

        coEvery { databaseSource.getUserById(any()) } returns userLocal

        val userLocalSearch = userDetailsRepository.getUserById(0)

        assertEquals(userLocal, userLocalSearch)
    }

    @Test
    fun `when searching for local user it returns empty user`() = runTest {
        coEvery { databaseSource.getUserById(any()) } returns null

        val userLocalSearch = userDetailsRepository.getUserById(0)

        assertEquals(null, userLocalSearch)
    }

    @Test
    fun `when searching for local user returns exception error`() = runTest {
        val sqliteException = SQLiteException("Column not found")

        coEvery { databaseSource.getUserById(any()) } throws sqliteException

        assertFailsWith<SQLiteException> {
            userDetailsRepository.getUserById(0)
        }

        coVerify { databaseSource.getUserById(any()) }
    }

}
