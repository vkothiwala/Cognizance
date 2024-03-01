package com.example.cognizance.domain.repositories

import androidx.paging.PagingData
import com.example.cognizance.data.models.EntityMoviesBookmark
import com.example.cognizance.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    val movies: Flow<PagingData<Movie>>
    val bookmarks: Flow<List<EntityMoviesBookmark>>
    suspend fun onFavouriteClick(movieId: Int)
}
