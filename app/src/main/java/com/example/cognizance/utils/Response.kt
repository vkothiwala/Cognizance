package com.example.cognizance.utils

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val error: Exception) : Response<T>()
}

fun <T, R> Response<T>.map(
    transform: (T) -> R
): Response<R> {
    return when (this) {
        is Response.Success -> Response.Success(transform(data))
        is Response.Error -> Response.Error(error)
    }
}
