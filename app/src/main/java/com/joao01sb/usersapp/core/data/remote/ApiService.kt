package com.joao01sb.usersapp.core.data.remote

import com.joao01sb.usersapp.core.domain.model.User
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("/users")
    suspend fun getAllUSers() : Response<List<User>>

}