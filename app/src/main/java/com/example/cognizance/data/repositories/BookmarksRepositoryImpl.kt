package com.example.cognizance.data.repositories

import com.example.cognizance.data.local.BookmarksLocalSource
import com.example.cognizance.data.mappers.toMovieBookmark
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.domain.repositories.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Bookmark is a local feature. It has no remote support. Deleting storage will
 * result in loss of bookmarks.
 * Bookmark storage keeps track of movieId. If movieId is found in storage then it
 * has been bookmarked.
 * */
class BookmarksRepositoryImpl @Inject constructor(
    private val bookmarksLocalSource: BookmarksLocalSource
) : BookmarksRepository {

    override val bookmarks: Flow<List<MovieBookmark>> = bookmarksLocalSource.bookmarks
        .map { entityBookmarks ->
            entityBookmarks.map {
                it.toMovieBookmark()
            }
        }

    override suspend fun onBookmarkClick(movieId: Int) {
        bookmarksLocalSource.onBookmarkClick(movieId)
    }
}
