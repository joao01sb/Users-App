package com.joao01sb.usersapp.home.data.datasource

import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.home.domain.datasource.UserLocalDataSource
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImp(
    private val dao: UserDao
) : UserLocalDataSource {

    override suspend fun getUsers(): Flow<List<UserEntity>> {
        return dao.allUsers()
    }

    override suspend fun insertUser(users: List<UserEntity>) {
        dao.insertUser(users)
    }

}
