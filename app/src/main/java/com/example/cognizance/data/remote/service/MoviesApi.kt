package com.example.cognizance.data.remote.service

import com.example.cognizance.data.remote.models.ApiMovieDetails
import com.example.cognizance.data.remote.models.ApiMovieVideos
import com.example.cognizance.data.remote.models.ApiMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
    ): ApiMoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
    ): ApiMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
    ): ApiMoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
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
    ): ApiMovieVideos

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = EN_US
    ): ApiMoviesResponse

    companion object {
        const val API_KEY = "ad35eeedf999e78fd5e38d13c53f5ad8"
        const val EN_US = "en-US"
    }
}
