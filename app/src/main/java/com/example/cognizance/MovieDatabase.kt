package com.example.cognizance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.data.services.MovieDao

@Database(entities = [EntityMovie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
