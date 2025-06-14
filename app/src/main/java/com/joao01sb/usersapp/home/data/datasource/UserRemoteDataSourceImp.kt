package com.joao01sb.usersapp.home.data.datasource

import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.data.remote.utils.safeApiCall
import com.joao01sb.usersapp.core.utils.ResultWrapper
import com.joao01sb.usersapp.home.domain.datasource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.coroutineContext

class UserRemoteDataSourceImp(
    private val apiService: ApiService
) : UserRemoteDataSource {

    override suspend fun getUsers(): Flow<ResultWrapper<List<UserDto>>>{
        return flow {
            val result = safeApiCall(Dispatchers.IO) {
                apiService.getAllUSers()
            }
            emit(result)
        }
    }


}