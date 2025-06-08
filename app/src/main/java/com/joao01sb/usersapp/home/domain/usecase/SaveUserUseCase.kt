package com.joao01sb.usersapp.home.domain.usecase

import com.joao01sb.usersapp.core.domain.model.User

interface SaveUserUseCase {

    suspend fun invoke(users: List<User>)

}