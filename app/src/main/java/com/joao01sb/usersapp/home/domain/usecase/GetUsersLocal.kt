package com.joao01sb.usersapp.home.domain.usecase

import com.joao01sb.usersapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetUsersLocal {

    suspend fun invoke() : Flow<List<User>>

}