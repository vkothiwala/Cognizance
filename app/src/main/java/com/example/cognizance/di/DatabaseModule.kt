package com.example.cognizance.di

import android.content.Context
import androidx.room.Room
import com.example.cognizance.data.local.MoviesDatabase
import com.example.cognizance.data.local.dao.MoviesBookmarkDao
import com.example.cognizance.data.local.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context = context,
            MoviesDatabase::class.java,
            name = "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MoviesDatabase): MoviesDao {
        return movieDatabase.getMovieDao()
    }

    @Provides
    fun provideMoviesBookmarkDao(movieDatabase: MoviesDatabase): MoviesBookmarkDao {
        return movieDatabase.getMoviesBookmarkDao()
    }
}
