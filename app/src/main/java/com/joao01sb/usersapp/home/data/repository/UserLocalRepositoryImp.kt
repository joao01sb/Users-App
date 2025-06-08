package com.joao01sb.usersapp.home.data.repository

import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.home.domain.datasource.UserLocalDataSource
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import kotlinx.coroutines.flow.Flow

class UserLocalRepositoryImp(
    private val dataSource: UserLocalDataSource
) : UserLocalRepository{

    override suspend fun getUsersLocal(): Flow<List<UserEntity>> {
       return dataSource.getUsers()
    }

    override suspend fun insertUser(users: List<UserEntity>) {
        dataSource.insertUser(users)
    }


}