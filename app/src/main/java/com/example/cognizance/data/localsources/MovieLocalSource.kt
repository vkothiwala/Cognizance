package com.example.cognizance.data.localsources

import android.content.Context
import androidx.room.Room
import com.example.cognizance.MovieDatabase
import com.example.cognizance.data.models.EntityMovie
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalSource @Inject constructor(
    @ApplicationContext context: Context
) {

    private val db = Room.databaseBuilder(
        context = context,
        MovieDatabase::class.java,
        name = "movie_databse"
    ).build()

    val movies: Flow<List<EntityMovie>> = db.movieDao().getMovies()

    fun saveMovies(movies: List<EntityMovie>) {
        db.movieDao().deleteAll()
        db.movieDao().insertAll(movies)
    }
}
