package com.joao01sb.usersapp.home.data.datasource

import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRemoteDataSourceImp(
    private val apiService: ApiService
) : UserRemoteDataSource {

    override suspend fun getUsers(): Flow<ResultWrapper<List<UserDto>>> {
        val result = apiService.getAllUSers()
        return if (result.isSuccessful) {
            flowOf(ResultWrapper.Sucess(result.body() ?: emptyList()))
        } else {
            flowOf(ResultWrapper.Error(RuntimeException(result.errorBody()?.string())))
        }
    }


}