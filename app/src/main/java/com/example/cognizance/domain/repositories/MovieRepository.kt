package com.example.cognizance.domain.repositories

import com.example.cognizance.domain.models.Movie
import com.example.cognizance.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    val movies: Flow<List<Movie>>
    suspend fun getMovies(): Response<Unit>
}
