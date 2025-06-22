package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersLocalImp(
    private val userLocalRepository: UserLocalRepository
) : GetUsersLocal{

    override suspend fun invoke(): Flow<List<User>> {
        return userLocalRepository.getUsersLocal()
            .map { list -> list.map { it.toModel() } }
    }

}
