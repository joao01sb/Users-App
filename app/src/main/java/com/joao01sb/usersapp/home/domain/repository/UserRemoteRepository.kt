package com.joao01sb.usersapp.home.domain.repository

import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface UserRemoteRepository {

    suspend fun getUsersRemote() : Flow<ResultWrapper<List<UserDto>>>

}
