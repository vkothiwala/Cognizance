package com.example.cognizance.data.repositories

import com.example.cognizance.data.localsources.MovieLocalSource
import com.example.cognizance.data.mappers.toEntityMovie
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.data.remotesources.MovieRemoteSource
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.repositories.MovieRepository
import com.example.cognizance.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteSource: MovieRemoteSource,
    private val localSource: MovieLocalSource
) : MovieRepository {

    override val movies: Flow<List<Movie>> = localSource.movies.map { entityMovies ->
        entityMovies.map { it.toMovie() }
    }

    override suspend fun getMovies(): Response<Unit> {
        return when (val result = remoteSource.getMovies()) {
            is Response.Success -> {
                val entityMovies: List<EntityMovie> = result.data.map { apiMovie ->
                    apiMovie.toEntityMovie()
                }
                localSource.saveMovies(entityMovies)
                Response.Success(Unit)
            }

            is Response.Error -> {
                Response.Error(result.e)
            }
        }
    }
}
