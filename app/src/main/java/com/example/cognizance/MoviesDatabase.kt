package com.example.cognizance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cognizance.data.localsources.MovieDao
import com.example.cognizance.data.models.EntityMovie

@Database(
    entities = [EntityMovie::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}
