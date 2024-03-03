package com.example.cognizance.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.data.remote.service.MoviesApi
import com.example.cognizance.utils.MovieCategoryType

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val movieCategoryType: MovieCategoryType
) : PagingSource<Int, ApiMovie>() {

    override fun getRefreshKey(state: PagingState<Int, ApiMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiMovie> {
        return try {
            val page = params.key ?: 1
            val apiMovieResponse = when (movieCategoryType) {
                is MovieCategoryType.Popular -> moviesApi.getPopularMovies(page = page)
                is MovieCategoryType.Upcoming -> throw IllegalStateException("Upcoming can not be type for MoviesPagingSource")
            }
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
