package com.example.cognizance.data.remote

import com.example.cognizance.data.remote.models.ApiMovieDetails
import com.example.cognizance.data.remote.service.MoviesApi
import com.example.cognizance.utils.Response
import javax.inject.Inject

class MoviesRemoteSource @Inject constructor(
    private val moviesApi: MoviesApi
) {

    suspend fun getMovieDetails(movieId: Int): Response<ApiMovieDetails> {
        return try {
            val apiMovieDetails = moviesApi.getMovieDetails(movieId)
            Response.Success(apiMovieDetails)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}
