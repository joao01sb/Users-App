package com.joao01sb.usersapp.details.data.repository

import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.details.domain.datasource.LocalDataSource
import com.joao01sb.usersapp.details.domain.repository.UserDetailsRepository

class UserDetailsRepositoryImp(
    private val datasource: LocalDataSource
) : UserDetailsRepository {
    override suspend fun getUserById(id: Int): UserEntity? {
        return datasource.getUserById(id)
    }
}