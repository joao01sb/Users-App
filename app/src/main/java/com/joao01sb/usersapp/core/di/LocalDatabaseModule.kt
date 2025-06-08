package com.joao01sb.usersapp.core.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.joao01sb.usersapp.core.data.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDatabaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "users_app_db"
        ).build()
    }

    single {
        get<AppDatabase>().usersDao()
    }

}