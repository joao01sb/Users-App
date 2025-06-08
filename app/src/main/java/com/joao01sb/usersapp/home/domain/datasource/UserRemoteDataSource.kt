package com.joao01sb.usersapp.home.domain.datasource

import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun getUsers() : Flow<ResultWrapper<List<User>>>
}