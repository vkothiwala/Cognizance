package com.example.cognizance.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cognizance.data.local.MoviesLocalSource
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.mappers.toMovieDetailsMapper
import com.example.cognizance.data.mappers.toMovieVideo
import com.example.cognizance.data.remote.MoviesRemoteSource
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.di.PagingSourceModule
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieDetails
import com.example.cognizance.domain.models.MovieVideo
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.utils.MovieCategoryType
import com.example.cognizance.utils.Response
import com.example.cognizance.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getMovies(category: MovieCategoryType): Response<List<Movie>> {
        return when (category) {
            MovieCategoryType.Upcoming -> moviesRemoteSource.getUpcomingMovies(1)
            MovieCategoryType.Popular -> moviesRemoteSource.getUpcomingMovies(1)
            MovieCategoryType.TopRated -> moviesRemoteSource.getUpcomingMovies(1)
        }.map { response ->
            response.results.map { it.toMovie() }
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

    override suspend fun getMovieVideos(movieId: Int): Response<List<MovieVideo>> {
        return moviesRemoteSource.getMovieVideos(movieId).map { apiMoviesVideos ->
            apiMoviesVideos.moviesVideos.map {
                it.toMovieVideo()
            }
        }
    }
}
