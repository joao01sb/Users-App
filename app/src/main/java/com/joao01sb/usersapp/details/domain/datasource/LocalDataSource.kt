package com.joao01sb.usersapp.details.domain.datasource

import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.domain.model.User

interface LocalDataSource {

    suspend fun getUserById(id: Int): UserEntity?

}