package com.joao01sb.usersapp.home.domain.repository

import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun invoke() : Flow<ResultApiService<List<User>>>

}