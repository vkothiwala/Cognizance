package com.example.cognizance.data.localsources

import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.data.services.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalSource @Inject constructor(
    private val movieDao: MovieDao
) {

    val movies: Flow<List<EntityMovie>> = movieDao.getMovies()

    fun saveMovies(movies: List<EntityMovie>) {
        movieDao.deleteAll()
        movieDao.insertAll(movies)
    }
}
