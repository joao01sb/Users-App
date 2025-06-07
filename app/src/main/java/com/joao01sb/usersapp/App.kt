package com.joao01sb.usersapp

import android.app.Application
import com.joao01sb.usersapp.core.di.networkModule
import com.joao01sb.usersapp.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(networkModule + homeModule)
        }
    }

}