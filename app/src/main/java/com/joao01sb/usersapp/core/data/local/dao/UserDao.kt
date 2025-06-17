package com.joao01sb.usersapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<UserEntity>)

    @Query("SELECT * FROM _users")
    fun allUsers() : Flow<List<UserEntity>>

    @Query("SELECT * FROM _users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

}