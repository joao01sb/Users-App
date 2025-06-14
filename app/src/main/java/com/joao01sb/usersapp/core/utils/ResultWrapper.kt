package com.joao01sb.usersapp.core.utils

sealed class ResultWrapper<out T> {
    object Loading: ResultWrapper<Nothing>()
    data class Sucess<T>(val result: T) : ResultWrapper<T>()
    data class Error(val error: Throwable) : ResultWrapper<Nothing>()

    inline fun <R> map(transform: (T) -> R): ResultWrapper<R> = when (this) {
        is Loading -> Loading
        is Sucess -> Sucess(transform(result))
        is Error -> Error(error)
    }
}
