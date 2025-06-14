package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemoteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsersRemoteUseCaseImp(
    private val userRepository: UserRemoteRepository
) : GetUsersRemoteUseCase {

    override suspend fun invoke(): Flow<ResultWrapper<List<User>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            userRepository.getUsersRemote().collect {
                if (it is ResultWrapper.Success && it.result.isNotEmpty()) {
                    emit(it.map { it.map { it.toModel() } })
                } else {
                    emit(ResultWrapper.Error(RuntimeException("empty users")))
                }
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e))
        }
    }
}