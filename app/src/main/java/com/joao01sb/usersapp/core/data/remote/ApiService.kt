package com.joao01sb.usersapp.core.data.remote

import com.joao01sb.usersapp.core.data.remote.dto.UserDto
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.core.utils.USERS_ROUTE
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET(USERS_ROUTE)
    suspend fun getAllUSers() :List<UserDto>

}
