package com.example.cognizance.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.cognizance.MoviesDatabase
import com.example.cognizance.data.local.MoviesRemoteMediator
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.remote.MoviesPagingSource
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.data.remote.service.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class PagingSourceModule {

    private val pagingConfig = PagingConfig(
        pageSize = 30,
        prefetchDistance = 5
    )

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Named("upcoming_movies")
    fun provideMoviesRemoteMediator(
        moviesDatabase: MoviesDatabase,
        moviesApi: MoviesApi
    ): Pager<Int, EntityMovie> {
        return Pager(
            config = pagingConfig,
            remoteMediator = MoviesRemoteMediator(
                moviesApi = moviesApi,
                moviesDatabase = moviesDatabase
            ),
            pagingSourceFactory = {
                moviesDatabase.getMovieDao().getMovies()
            }
        )
    }

    @Provides
    @Named("popular_movies")
    fun provideMoviesPagingSource(
        moviesApi: MoviesApi
    ): Pager<Int, ApiMovie> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviesPagingSource(moviesApi)
            }
        )
    }

    companion object {
        const val UPCOMING_MOVIES = "upcoming_movies"
        const val POPULAR_MOVIES = "popular_movies"
    }
}
