package com.example.cognizance.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cognizance.data.local.MoviesLocalSource
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.mappers.toMovieDetailsMapper
import com.example.cognizance.data.remote.MoviesRemoteSource
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.di.PagingSourceModule
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieDetails
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.utils.Response
import com.example.cognizance.utils.dataOrNull
import com.example.cognizance.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Named

class MoviesRepositoryImpl @Inject constructor(
    @Named(PagingSourceModule.POPULAR_MOVIES) popularMovies: Pager<Int, ApiMovie>,
    @Named(PagingSourceModule.UPCOMING_MOVIES) upcomingMovies: Pager<Int, EntityMovie>,
    @Named(PagingSourceModule.TOP_RATED_MOVIES) topRatedMovies: Pager<Int, ApiMovie>,
    private val moviesRemoteSource: MoviesRemoteSource,
    private val moviesLocalSource: MoviesLocalSource
) : MoviesRepository {

    override val upcomingMovies: Flow<PagingData<Movie>> = upcomingMovies.flow
        .map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }

    override val popularMovies: Flow<PagingData<Movie>> = popularMovies.flow
        .map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }

    override val topRatedMovies: Flow<PagingData<Movie>> = topRatedMovies.flow
        .map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }

    override suspend fun getMovieDetails(movieId: Int): Response<MovieDetails> {
        return moviesRemoteSource.getMovieDetails(movieId).map {
            it.toMovieDetailsMapper()
        }
    }

    override suspend fun getMovieById(movieId: Int): Response<Movie> {
        moviesLocalSource.getMovieById(movieId)?.let { entityMovie ->
            return Response.Success(entityMovie.toMovie())
        }
        // If movie is not found locally, make remote call to fetch data
        return moviesRemoteSource.getMovieDetails(movieId).map { it.toMovie() }
    }

    override suspend fun getMovieVideoId(movieId: Int): Response<String> {
        val videoId = moviesRemoteSource.getMovieVideoId(movieId).dataOrNull()?.results?.firstOrNull()?.key
        return if (videoId == null) {
            Response.Error(IllegalStateException())
        } else {
            Response.Success(videoId)
        }
    }
}
