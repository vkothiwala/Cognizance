package com.example.cognizance.data.local

import com.example.cognizance.data.local.dao.MoviesDao
import com.example.cognizance.data.local.models.EntityMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalSource @Inject constructor(
    private val moviesDao: MoviesDao
) {
    suspend fun getMovieById(movieId: Int): EntityMovie? = withContext(Dispatchers.IO) {
        moviesDao.getMovieById(movieId)
    }
}
