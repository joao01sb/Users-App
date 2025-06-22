package com.joao01sb.usersapp.details.data.usecase

import androidx.sqlite.SQLiteException
import com.joao01sb.usersapp.core.domain.mapper.toModel
import com.joao01sb.usersapp.core.domain.model.User
import com.joao01sb.usersapp.details.domain.repository.UserDetailsRepository
import com.joao01sb.usersapp.details.domain.usecase.GetUserById
import java.io.IOException

class GetUserByIdImp(
    private val repository: UserDetailsRepository
) : GetUserById {
    override suspend fun invoke(id: Int): Result<User> {
        return try {
            repository.getUserById(id)?.let { Result.success(it.toModel()) }
                ?: Result.failure(Exception("User not found"))
        } catch (e: SQLiteException) {
            Result.failure(IOException("Erro no banco de dados", e))
        } catch (e: IllegalStateException) {
            Result.failure(IOException("Erro interno ao acessar o banco", e))
        }

    }
}
