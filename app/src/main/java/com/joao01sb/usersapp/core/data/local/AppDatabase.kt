package com.joao01sb.usersapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao() : UserDao
}