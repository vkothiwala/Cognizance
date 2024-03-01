package com.example.cognizance.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.cognizance.MoviesDatabase
import com.example.cognizance.data.local.MoviesRemoteMediator
import com.example.cognizance.data.local.dao.MoviesBookmarkDao
import com.example.cognizance.data.local.dao.MoviesDao
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.remote.MoviesApi
import com.example.cognizance.data.remote.MoviesPagingSource
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.data.repositories.BookmarksRepositoryImpl
import com.example.cognizance.data.repositories.MoviesRepositoryImpl
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieBindModule {

    @Binds
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindBookmarksRepository(bookmarksRepositoryImpl: BookmarksRepositoryImpl): BookmarksRepository
}

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }
}

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

@Module
@InstallIn(SingletonComponent::class)
class PagingSourceModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("movies_remote_mediator")
    fun provideMoviesRemoteMediator(
        moviesDatabase: MoviesDatabase,
        moviesApi: MoviesApi
    ): Pager<Int, EntityMovie> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 1
            ),
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
    @Named("movies_paging_source")
    fun provideMoviesPagingSource(
        moviesApi: MoviesApi
    ): Pager<Int, ApiMovie> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                MoviesPagingSource(moviesApi)
            }
        )
    }
}
