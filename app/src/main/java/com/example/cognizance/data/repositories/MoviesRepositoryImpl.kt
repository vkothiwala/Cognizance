package com.example.cognizance.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.mappers.toMovieDetailsMapper
import com.example.cognizance.data.remote.MoviesRemoteSource
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieDetails
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.utils.Response
import com.example.cognizance.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class MoviesRepositoryImpl @Inject constructor(
    @Named("movies_paging_source") pagingSource: Pager<Int, ApiMovie>,
    @Named("movies_remote_mediator") remoteMediator: Pager<Int, EntityMovie>,
    private val moviesRemoteSource: MoviesRemoteSource
) : MoviesRepository {

    override val upcomingMovies: Flow<PagingData<Movie>> = remoteMediator.flow
        .map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }

    override val popularMovies: Flow<PagingData<Movie>> = pagingSource.flow
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
}
