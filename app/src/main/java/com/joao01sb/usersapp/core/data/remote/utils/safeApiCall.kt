package com.joao01sb.usersapp.core.data.remote.utils

import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    ResultWrapper.Error(IOException("Error network connection"))
                }
                is HttpException -> {
                    ResultWrapper.Error(IOException("Error server connection"))
                }
                else -> {
                    ResultWrapper.Error(throwable)
                }
            }
        }
    }
}
