package com.example.cognizance.di

import com.example.cognizance.data.repositories.MovieRepositoryImpl
import com.example.cognizance.data.services.MoviesApi
import com.example.cognizance.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
class MovieProvideModule {

    @Singleton
    @Provides
    fun provideMovieApi(): MoviesApi {
        return getRetrofit().create(MoviesApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
