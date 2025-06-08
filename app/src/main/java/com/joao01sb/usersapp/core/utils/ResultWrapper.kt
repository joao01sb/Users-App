package com.joao01sb.usersapp.core.utils

sealed class ResultWrapper<out T> {
    object Loading: ResultWrapper<Nothing>()
    data class Sucess<T>(val result: T) : ResultWrapper<T>()
    data class Error(val error: Throwable) : ResultWrapper<Nothing>()
}