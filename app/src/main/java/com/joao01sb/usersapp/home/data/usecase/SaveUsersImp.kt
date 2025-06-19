package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.core.domain.mapper.toEntity
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import com.joao01sb.usersapp.home.domain.usecase.SaveUsers

class SaveUsersImp(
    private val userLocalRepository: UserLocalRepository
) : SaveUsers {
    override suspend fun invoke(users: List<User>) {
        val usersMap = users.map { it.toEntity() }
        userLocalRepository.insertUser(usersMap)
    }

}