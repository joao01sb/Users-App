package com.joao01sb.usersapp.home.domain.repository

import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {

    suspend fun getUsersLocal() : Flow<List<UserEntity>>

    suspend fun insertUser(users: List<UserEntity>)

}
