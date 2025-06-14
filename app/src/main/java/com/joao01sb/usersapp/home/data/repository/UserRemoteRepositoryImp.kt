package com.joao01sb.usersapp.home.data.repository

import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import kotlinx.coroutines.flow.Flow

class UserRemoteRepositoryImp(
    private val dataSource: UserRemoteDataSource
) : UserRemoteRepository {
    override suspend fun getUsersRemote(): Flow<ResultWrapper<List<UserDto>>> {
        return dataSource.getUsers()
    }
}