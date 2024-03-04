package com.example.cognizance.data.local

import com.example.cognizance.data.local.dao.MoviesBookmarkDao
import com.example.cognizance.data.local.models.EntityMoviesBookmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarksLocalSource @Inject constructor(
    private val moviesBookmarkDao: MoviesBookmarkDao
) {
    val bookmarks: Flow<List<EntityMoviesBookmark>> = moviesBookmarkDao.getAllMoviesBookmark()

    suspend fun onBookmarkClick(movieId: Int) = withContext(Dispatchers.IO) {
        val entityMoviesBookmark = moviesBookmarkDao.findMovie(movieId = movieId)
        if (entityMoviesBookmark == null) {
            // If movie being bookmarked is not found in table, then bookmark it by adding it to table.
            moviesBookmarkDao.addMovie(
                EntityMoviesBookmark(
                    id = movieId
                )
            )
        } else {
            // If movie being bookmarked exists in table, un-bookmark it by deleting it from table.
            moviesBookmarkDao.deleteMovie(
                EntityMoviesBookmark(
                    id = movieId
                )
            )
        }
    }
}
