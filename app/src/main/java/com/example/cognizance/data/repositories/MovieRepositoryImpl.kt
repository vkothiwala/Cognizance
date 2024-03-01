package com.example.cognizance.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.example.cognizance.MoviesDatabase
import com.example.cognizance.data.mappers.toMovie
import com.example.cognizance.data.mappers.toMovieBookmark
import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.data.models.EntityMoviesBookmark
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class MovieRepositoryImpl @Inject constructor(
    @Named("movies_paging_source") pagingSource: Pager<Int, ApiMovie>,
    @Named("movies_remote_mediator") remoteMediator: Pager<Int, EntityMovie>,
    private val movieDatabase: MoviesDatabase
) : MovieRepository {

    override val bookmarks: Flow<List<MovieBookmark>> = movieDatabase
        .getMoviesBookmarkDao()
        .getAllMoviesBookmark()
        .map { entityBookmarks ->
            entityBookmarks.map {
                it.toMovieBookmark()
            }
        }

    override val movies: Flow<PagingData<Movie>> = remoteMediator.flow
        .map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }

    override suspend fun onFavouriteClick(movieId: Int) {
        movieDatabase.withTransaction {
            val bookmarkDao = movieDatabase.getMoviesBookmarkDao()
            val bookmarkStatus: Boolean = bookmarkDao.getBookmarkStatus(movieId = movieId) ?: false
            bookmarkDao.bookmarkMovie(
                EntityMoviesBookmark(
                    id = movieId,
                    bookmark = bookmarkStatus.not()
                )
            )
        }
    }
}
