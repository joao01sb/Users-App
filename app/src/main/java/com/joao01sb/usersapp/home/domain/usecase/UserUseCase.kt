package com.joao01sb.usersapp.home.domain.usecase

import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    suspend fun invoke() : Flow<ResultApiService<List<User>>>

}