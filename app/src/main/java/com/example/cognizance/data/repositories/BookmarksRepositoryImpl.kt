package com.example.cognizance.data.repositories

import androidx.room.withTransaction
import com.example.cognizance.MoviesDatabase
import com.example.cognizance.data.local.models.EntityMoviesBookmark
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.mappers.toMovieBookmark
import com.example.cognizance.data.remote.MoviesRemoteSource
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.utils.dataOrNull
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
    private val moviesDatabase: MoviesDatabase,
    private val moviesRemoteSource: MoviesRemoteSource
) : BookmarksRepository {

    override val bookmarks: Flow<List<MovieBookmark>> = moviesDatabase
        .getMoviesBookmarkDao()
        .getAllMoviesBookmark()
        .map { entityBookmarks ->
            entityBookmarks.map {
                it.toMovieBookmark()
            }
        }

    override val bookmarkedMovies: Flow<List<Movie>> = moviesDatabase.getMoviesBookmarkDao()
        .getAllMoviesBookmark()
        .map { entityBookmarks ->
            entityBookmarks.mapNotNull {
                moviesDatabase.withTransaction {
                    moviesDatabase.getMovieDao().getMovieById(it.id)?.toMovie()
                } ?: run {
                    moviesRemoteSource.getMovieDetails(movieId = it.id).dataOrNull()?.toMovie()
                }
            }
        }

    override suspend fun onBookmarkClick(movieId: Int) {
        moviesDatabase.withTransaction {
            val bookmarkDao = moviesDatabase.getMoviesBookmarkDao()
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
