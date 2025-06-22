package com.joao01sb.usersapp.core.di

import com.joao01sb.usersapp.core.data.remote.ApiService
import com.joao01sb.usersapp.core.utils.BASE_URL
import com.joao01sb.usersapp.core.utils.TIMEOUT_REQUEST
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { providerOkHttpClient() }
    single {
        providerRetrofit<ApiService>(
            okHttpClient = get(),
            url = BASE_URL
        )
    }
}

fun providerOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .callTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
        .build()


inline fun <reified T> providerRetrofit(
    okHttpClient: OkHttpClient,
    url: String
) : T {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(T::class.java)
}
