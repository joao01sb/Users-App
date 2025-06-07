package com.joao01sb.usersapp.core.di

import com.joao01sb.usersapp.core.data.remote.ApiService
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
            url = "https://jsonplaceholder.typicode.com"
        )
    }
}

sealed class ResultApiService<out T> {
    object Loading: ResultApiService<Nothing>()
    data class Sucess<T>(val result: T) : ResultApiService<T>()
    data class Error(val error: Throwable) : ResultApiService<Nothing>()
}

fun providerOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
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