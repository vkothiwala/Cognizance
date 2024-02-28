package com.example.cognizance.data.remotesources

import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.services.MoviesApi
import com.example.cognizance.utils.Response
import javax.inject.Inject

class MovieRemoteSource @Inject constructor(
    private val moviesApi: MoviesApi
) {

    suspend fun getMovies(): Response<List<ApiMovie>> {
        return try {
            moviesApi.getMovies(page = 1)
                .body()?.let {
                    Response.Success(it.results)
                } ?: run {
                Response.Error(IllegalStateException("Unknown Error occurred"))
            }
        } catch (e: Exception) {
            println("Captured exception: $e")
            Response.Error(e)
        }
    }
}
