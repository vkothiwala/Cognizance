package com.example.cognizance.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.cognizance.data.local.dao.MoviesDao
import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.mappers.toEntityMovie
import com.example.cognizance.data.remote.service.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
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
            val apiMovieResponse = moviesApi.getUpcomingMovies(page = page)

            val movies = apiMovieResponse.results
            val endOfPaginationReached = movies.isEmpty()

            withContext(Dispatchers.IO) {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteAll()
                    moviesDao.resetPrimaryKeyAutoIncrementValue()
                }
                val entityMovies = movies.map {
                    it.toEntityMovie(
                        currentPage = apiMovieResponse.page
                    )
                }
                moviesDao.insertAll(entityMovies)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }
}
