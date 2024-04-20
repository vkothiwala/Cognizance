package com.example.cognizance.utils

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val error: Exception) : Response<Nothing>()
}

fun <T, R> Response<T>.map(
    transform: (T) -> R
): Response<R> {
    return when (this) {
        is Response.Success -> Response.Success(transform(data))
        is Response.Error -> Response.Error(error)
    }
}

fun <T> Response<T>.onSuccess(
    block: (T) -> Unit
): Response<T> {
    return when (this) {
        is Response.Success -> {
            block(data)
            this
        }

        is Response.Error -> this
    }
}

fun <T> Response<T>.onError(
    block: (Exception) -> Unit
): Response<T> {
    return when (this) {
        is Response.Success -> this
        is Response.Error -> {
            block(error)
            this
        }
    }
}

fun <T> Response<T>.dataOrNull(): T? {
    return when (this) {
        is Response.Success -> data
        is Response.Error -> null
    }
}
