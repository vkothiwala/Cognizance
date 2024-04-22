package com.example.cognizance.data.remote

import com.example.cognizance.data.remote.models.ApiMovieDetails
import com.example.cognizance.data.remote.models.ApiMovieVideos
import com.example.cognizance.data.remote.models.ApiMoviesResponse
import com.example.cognizance.data.remote.service.MoviesApi
import com.example.cognizance.utils.Response
import javax.inject.Inject

class MoviesRemoteSource @Inject constructor(
    private val moviesApi: MoviesApi
) {

    suspend fun getUpcomingMovies(page: Int): Response<ApiMoviesResponse> {
        return try {
            val moviesResponse = moviesApi.getUpcomingMovies(page)
            Response.Success(moviesResponse)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    suspend fun getPopularMovies(page: Int): Response<ApiMoviesResponse> {
        return try {
            val moviesResponse = moviesApi.getPopularMovies(page)
            Response.Success(moviesResponse)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    suspend fun getTopRatedMovies(page: Int): Response<ApiMoviesResponse> {
        return try {
            val moviesResponse = moviesApi.getTopRatedMovies(page)
            Response.Success(moviesResponse)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    suspend fun getMovieDetails(movieId: Int): Response<ApiMovieDetails> {
        return try {
            val apiMovieDetails = moviesApi.getMovieDetails(movieId)
            Response.Success(apiMovieDetails)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    suspend fun getMovieVideos(movieId: Int): Response<ApiMovieVideos> {
        return try {
            val videosResponse = moviesApi.getVideos(movieId = movieId)
            Response.Success(videosResponse)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    suspend fun searchMovie(query: String): Response<ApiMoviesResponse> {
        return try {
            val searchMoviesResponse = moviesApi.searchMovie(query = query)
            Response.Success(searchMoviesResponse)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}
