package com.example.cognizance.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cognizance.data.local.dao.MoviesBookmarkDao
import com.example.cognizance.data.local.dao.MoviesDao
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.local.models.EntityMoviesBookmark

@Database(
    entities = [EntityMovie::class, EntityMoviesBookmark::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
    abstract fun getMoviesBookmarkDao(): MoviesBookmarkDao
}
