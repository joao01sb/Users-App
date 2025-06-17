package com.joao01sb.usersapp.details.data.usecase

import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.details.domain.repository.UserDetailsRepository
import com.joao01sb.usersapp.details.domain.usecase.GetUserById

class GetUserByIdImp(
    private val repository: UserDetailsRepository
) : GetUserById {
    override suspend fun invoke(id: Int): Result<User> {
        return try {
            repository.getUserById(id)?.let { Result.success(it.toModel()) }
                ?: Result.failure(Exception("User not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}