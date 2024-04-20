package com.example.cognizance.data.remote.service

import com.example.cognizance.data.remote.models.ApiMovieDetails
import com.example.cognizance.data.remote.models.ApiMoviesResponse
import com.example.cognizance.data.remote.models.ApiVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
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

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US,
        @Query("page") page: Int
    ): ApiMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
    ): ApiMovieDetails

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
    ): ApiVideosResponse

    companion object {
        const val API_KEY = "ad35eeedf999e78fd5e38d13c53f5ad8"
        const val EN_US = "en-US"
    }
}
