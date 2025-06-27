package com.joao01sb.usersapp.core.data.remote.utils

import com.google.gson.JsonParseException
import com.joao01sb.usersapp.core.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> = withContext(dispatcher) {
    try {
        ResultWrapper.Success(apiCall())
    } catch (e: HttpException) {
        ResultWrapper.Error(IOException("Error HTTP ${e.code()}: ${e.message()}", e))
    } catch (e: SocketTimeoutException) {
        ResultWrapper.Error(IOException("Response timeout", e))
    } catch (e: UnknownHostException) {
        ResultWrapper.Error(IOException("No internet connection", e))
    } catch (e: JsonParseException) {
        ResultWrapper.Error(IOException("Error processing server response", e))
    } catch (e: IOException) {
        ResultWrapper.Error(IOException("Connection error", e))
    }
}
