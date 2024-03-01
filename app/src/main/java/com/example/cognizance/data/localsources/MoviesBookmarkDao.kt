package com.example.cognizance.data.localsources

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.cognizance.data.models.EntityMoviesBookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesBookmarkDao {

    @Query("SELECT bookmark_flag FROM movies_bookmark WHERE id = :movieId")
    fun getBookmarkStatus(movieId: Int): Boolean?

    @Query("SELECT * FROM movies_bookmark")
    fun getAllMoviesBookmark(): Flow<List<EntityMoviesBookmark>>

    @Upsert
    fun bookmarkMovie(movie: EntityMoviesBookmark)
}
