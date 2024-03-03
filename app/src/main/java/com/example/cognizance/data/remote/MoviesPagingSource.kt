package com.example.cognizance.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.data.remote.service.MoviesApi

class MoviesPagingSource(
    private val moviesApi: MoviesApi
) : PagingSource<Int, ApiMovie>() {

    override fun getRefreshKey(state: PagingState<Int, ApiMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiMovie> {
        return try {
            val page = params.key ?: 1
            val apiMovieResponse = moviesApi.getPopularMovies(page = page)

            LoadResult.Page(
                data = apiMovieResponse.results,
                prevKey = if (apiMovieResponse.page == 1) {
                    null
                } else {
                    apiMovieResponse.page
                },
                nextKey = if (apiMovieResponse.page == apiMovieResponse.totalPages) {
                    null
                } else {
                    apiMovieResponse.page + 1
                }
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
