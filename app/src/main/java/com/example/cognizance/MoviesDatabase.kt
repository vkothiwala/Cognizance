package com.example.cognizance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cognizance.data.localsources.MovieDao
import com.example.cognizance.data.localsources.MoviesBookmarkDao
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.data.models.EntityMoviesBookmark

@Database(
    entities = [EntityMovie::class, EntityMoviesBookmark::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getMoviesBookmarkDao(): MoviesBookmarkDao
}
