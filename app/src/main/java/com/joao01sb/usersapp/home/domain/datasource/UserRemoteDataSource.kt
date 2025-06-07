package com.joao01sb.usersapp.home.domain.datasource

import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun getUsers() : Flow<ResultApiService<List<User>>>
}