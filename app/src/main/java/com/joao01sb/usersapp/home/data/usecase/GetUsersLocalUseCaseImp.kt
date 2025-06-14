package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.home.domain.repository.UserLocalRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersLocalUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersLocalUseCaseImp(
    private val userLocalRepository: UserLocalRepository
) : GetUsersLocalUseCase{

    override suspend fun invoke(): Flow<List<User>> {
        return userLocalRepository.getUsersLocal()
            .map { list -> list.map { it.toModel() } }
    }

}