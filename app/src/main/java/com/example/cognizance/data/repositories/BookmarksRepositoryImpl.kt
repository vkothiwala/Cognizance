package com.example.cognizance.data.repositories

import androidx.room.withTransaction
import com.example.cognizance.MoviesDatabase
import com.example.cognizance.data.mappers.toMovieBookmark
import com.example.cognizance.data.models.EntityMoviesBookmark
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.domain.repositories.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor(
    private val movieDatabase: MoviesDatabase
) : BookmarksRepository {

    override val bookmarks: Flow<List<MovieBookmark>> = movieDatabase
        .getMoviesBookmarkDao()
        .getAllMoviesBookmark()
        .map { entityBookmarks ->
            entityBookmarks.map {
                it.toMovieBookmark()
            }
        }

    /**
     * Bookmark is a local feature. It has no remote support. Deleting storage will
     * result in loss of bookmarks.
     * Bookmark storage keeps track of movieId. If movieId is found in storage then it
     * has been bookmarked.
     * */
    override suspend fun onBookmarkClick(movieId: Int) {
        movieDatabase.withTransaction {
            val bookmarkDao = movieDatabase.getMoviesBookmarkDao()
            val entityMoviesBookmark = bookmarkDao.findMovie(movieId = movieId)
            if (entityMoviesBookmark == null) {
                // If movie being bookmarked is not found in table, then bookmark it by adding it to table.
                bookmarkDao.addMovie(
                    EntityMoviesBookmark(
                        id = movieId
                    )
                )
            } else {
                // If movie being bookmarked exists in table, un-bookmark it by deleting it from table.
                bookmarkDao.deleteMovie(
                    EntityMoviesBookmark(
                        id = movieId
                    )
                )
            }
        }
    }
}
