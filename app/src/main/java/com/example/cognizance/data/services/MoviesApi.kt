package com.example.cognizance.data.services

import com.example.cognizance.data.models.ApiMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ad35eeedf999e78fd5e38d13c53f5ad8"

interface MoviesApi {

    @GET("3/movie/now_playing?api_key=$API_KEY&language=en-US")
    suspend fun getMovies(@Query("page") page: Int): Response<ApiMovieResponse>
}
