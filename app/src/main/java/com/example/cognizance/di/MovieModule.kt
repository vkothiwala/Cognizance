package com.example.cognizance.di

import android.content.Context
import androidx.room.Room
import com.example.cognizance.MovieDatabase
import com.example.cognizance.data.repositories.MovieRepositoryImpl
import com.example.cognizance.data.services.MovieDao
import com.example.cognizance.data.services.MoviesApi
import com.example.cognizance.domain.repositories.MovieRepository
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
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/"

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieBindModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
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
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context = context,
            MovieDatabase::class.java,
            name = "movie_database"
        ).build()
    }

    @Provides
    fun provideChannelDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }
}
