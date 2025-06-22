package com.joao01sb.usersapp.details.data.datasource

import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.details.domain.datasource.LocalDataSource

class LocalDataSourceImp(
    private val dao: UserDao
) : LocalDataSource  {
    override suspend fun getUserById(id: Int): UserEntity? {
        return dao.getUserById(id)
    }
}
