package com.joao01sb.usersapp.home.data.datasource

import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.core.di.ResultApiService
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRemoteDataSourceImp(
    private val apiService: ApiService
) : UserRemoteDataSource {

    override suspend fun getUsers(): Flow<ResultApiService<List<User>>> {
        val result = apiService.getAllUSers()
        return if (result.isSuccessful) {
            flowOf(ResultApiService.Sucess(result.body() ?: emptyList()))
        } else {
            flowOf(ResultApiService.Error(RuntimeException(result.errorBody()?.string())))
        }
    }


}