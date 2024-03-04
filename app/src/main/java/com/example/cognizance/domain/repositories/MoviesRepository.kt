package com.example.cognizance.domain.repositories

import androidx.paging.PagingData
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieDetails
import com.example.cognizance.utils.Response
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val upcomingMovies: Flow<PagingData<Movie>>
    val popularMovies: Flow<PagingData<Movie>>
    val topRatedMovies: Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetails>
    suspend fun getMovieById(movieId: Int): Response<Movie>
}
