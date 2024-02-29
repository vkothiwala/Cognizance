package com.example.cognizance.data.services

import com.example.cognizance.data.models.ApiMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US,
        @Query("page") page: Int
    ): Response<ApiMovieResponse>

    companion object {
        const val API_KEY = "ad35eeedf999e78fd5e38d13c53f5ad8"
        const val EN_US = "en-US"
    }
}
