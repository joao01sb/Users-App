package com.joao01sb.usersapp.home.data.repository

import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import com.joao01sb.usersapp.home.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImp(
    private val dataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun invoke(): Flow<ResultApiService<List<User>>> {
        return dataSource.getUsers()
    }
}