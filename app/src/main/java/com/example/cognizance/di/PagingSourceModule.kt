package com.example.cognizance.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.cognizance.data.local.MoviesDatabase
import com.example.cognizance.data.local.MoviesRemoteMediator
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.remote.MoviesPagingSource
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.data.remote.service.MoviesApi
import com.example.cognizance.utils.MovieCategoryType
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
    @Named(UPCOMING_MOVIES)
    fun provideUpcomingMoviesPager(
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
    @Named(POPULAR_MOVIES)
    fun providePopularMoviesPager(
        moviesApi: MoviesApi
    ): Pager<Int, ApiMovie> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviesPagingSource(
                    moviesApi = moviesApi,
                    movieCategoryType = MovieCategoryType.Popular
                )
            }
        )
    }

    @Provides
    @Named(TOP_RATED_MOVIES)
    fun provideTopRatedMoviesPager(
        moviesApi: MoviesApi
    ): Pager<Int, ApiMovie> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviesPagingSource(
                    moviesApi = moviesApi,
                    movieCategoryType = MovieCategoryType.TopRated
                )
            }
        )
    }

    companion object {
        const val UPCOMING_MOVIES = "upcoming_movies"
        const val POPULAR_MOVIES = "popular_movies"
        const val TOP_RATED_MOVIES = "top_rated_movies"
    }
}
