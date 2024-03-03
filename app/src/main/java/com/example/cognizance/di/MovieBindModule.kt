package com.example.cognizance.di

import com.example.cognizance.data.repositories.BookmarksRepositoryImpl
import com.example.cognizance.data.repositories.MoviesRepositoryImpl
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieBindModule {

    @Binds
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindBookmarksRepository(bookmarksRepositoryImpl: BookmarksRepositoryImpl): BookmarksRepository
}
