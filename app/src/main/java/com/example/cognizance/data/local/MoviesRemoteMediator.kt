package com.example.cognizance.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cognizance.MoviesDatabase
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.mappers.toEntityMovie
import com.example.cognizance.data.remote.MoviesApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
) : RemoteMediator<Int, EntityMovie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EntityMovie>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: run {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                lastItem.currentPage + 1
            }
        }

        return try {
            val apiMovieResponse = moviesApi.getMovies(page = page)

            val movies = apiMovieResponse.results
            val endOfPaginationReached = movies.isEmpty()

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.getMovieDao().deleteAll()
                    moviesDatabase.getMovieDao().resetPrimaryKeyAutoIncrementValue()
                }
                val entityMovies = movies.map {
                    it.toEntityMovie(
                        currentPage = apiMovieResponse.page
                    )
                }
                moviesDatabase.getMovieDao().insertAll(entityMovies)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }
}
