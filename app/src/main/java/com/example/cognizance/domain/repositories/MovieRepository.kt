package com.example.cognizance.domain.repositories

import androidx.paging.PagingData
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieBookmark
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    val movies: Flow<PagingData<Movie>>
    val bookmarks: Flow<List<MovieBookmark>>
    suspend fun onBookmarkClick(movieId: Int)
}
