package com.example.cognizance.utils

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val e: Exception) : Response<T>()
}
