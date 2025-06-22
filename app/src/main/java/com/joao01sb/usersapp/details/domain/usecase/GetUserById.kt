package com.joao01sb.usersapp.details.domain.usecase

import com.joao01sb.usersapp.core.domain.model.User

interface GetUserById {

    suspend operator fun invoke(id: Int): Result<User>

}
