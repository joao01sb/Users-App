package com.joao01sb.usersapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joao01sb.usersapp.core.data.local.dao.UserDao
import com.joao01sb.usersapp.core.data.local.entities.UserEntity
import com.joao01sb.usersapp.core.utils.DATABASE_VERSION

@Database(
    entities = [UserEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao() : UserDao
}
