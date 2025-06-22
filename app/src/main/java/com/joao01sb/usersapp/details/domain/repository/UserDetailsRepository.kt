package com.joao01sb.usersapp.details.domain.repository

import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.domain.model.User

interface UserDetailsRepository {

    suspend fun getUserById(id: Int): UserEntity?

}
