package com.joao01sb.usersapp.core.di

import androidx.room.Room
import com.joao01sb.usersapp.core.data.local.AppDatabase
import com.joao01sb.usersapp.core.utils.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDatabaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        get<AppDatabase>().usersDao()
    }

}
