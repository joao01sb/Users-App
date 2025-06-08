package com.joao01sb.usersapp.home.domain.usecase

import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetUsersRemoteUseCase {

    suspend fun invoke() : Flow<ResultWrapper<List<User>>>

}