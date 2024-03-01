package com.example.cognizance.domain.repositories

import com.example.cognizance.domain.models.MovieBookmark
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
    val bookmarks: Flow<List<MovieBookmark>>
    suspend fun onBookmarkClick(movieId: Int)
}
