package com.joao01sb.usersapp.home.data.usecase

import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.home.domain.repository.UserRepository
import com.joao01sb.usersapp.home.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserUseCaseImp(
    private val userRepository: UserRepository
) : UserUseCase {

    override suspend fun invoke(): Flow<ResultApiService<List<User>>> = flow {
        emit(ResultApiService.Loading)
        try {
            userRepository.invoke().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(ResultApiService.Error(e))
        }
    }
}