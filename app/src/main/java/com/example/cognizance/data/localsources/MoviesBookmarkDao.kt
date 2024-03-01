package com.example.cognizance.data.localsources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.cognizance.data.models.EntityMoviesBookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesBookmarkDao {

    @Query("SELECT * FROM movies_bookmark")
    fun getAllMoviesBookmark(): Flow<List<EntityMoviesBookmark>>

    @Query("SELECT * FROM movies_bookmark WHERE id = :movieId")
    fun findMovie(movieId: Int): EntityMoviesBookmark?

    @Upsert
    fun addMovie(movie: EntityMoviesBookmark)

    @Delete
    fun deleteMovie(movie: EntityMoviesBookmark)
}
