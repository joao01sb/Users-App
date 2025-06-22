package com.joao01sb.usersapp.home.data.usecase

import com.google.gson.JsonParseException
import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.repository.UserRemoteRepository
import com.joao01sb.usersapp.home.domain.usecase.GetUsersRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetUsersRemoteImp(
    private val userRepository: UserRemoteRepository
) : GetUsersRemote {

    override suspend fun invoke(): Flow<ResultWrapper<List<User>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            userRepository.getUsersRemote().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        if (result.result.isNotEmpty()) {
                            try {
                                val users = result.result.map { it.toModel() }
                                emit(ResultWrapper.Success(users))
                            } catch (e: IllegalArgumentException) {
                                emit(ResultWrapper.Error(e))
                            }
                        } else {
                            emit(ResultWrapper.Error(IllegalArgumentException("empty users")))
                        }
                    }

                    is ResultWrapper.Error -> {
                        emit(ResultWrapper.Error(result.error))
                    }

                    ResultWrapper.Loading -> emit(ResultWrapper.Loading)
                }
            }
        } catch (e: IllegalStateException) {
            emit(ResultWrapper.Error(e))
        } catch (e: IOException) {
            emit(ResultWrapper.Error(e))
        } catch (e: JsonParseException) {
            emit(ResultWrapper.Error(e))
        }
    }

}
