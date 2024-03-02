package com.example.cognizance.data.remote

import com.example.cognizance.data.remote.models.ApiMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US,
        @Query("page") page: Int
    ): ApiMoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US,
        @Query("page") page: Int
    ): ApiMoviesResponse

    companion object {
        const val API_KEY = "ad35eeedf999e78fd5e38d13c53f5ad8"
        const val EN_US = "en-US"
    }
}
