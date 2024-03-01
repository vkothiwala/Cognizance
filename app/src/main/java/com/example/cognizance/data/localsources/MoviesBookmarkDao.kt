package com.example.cognizance.data.localsources

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.cognizance.data.models.EntityMoviesBookmark

@Dao
interface MoviesBookmarkDao {

    @Query("SELECT bookmark_flag FROM movies_bookmark WHERE id = :movieId")
    fun getBookmarkStatus(movieId: Int): Boolean?

    @Upsert
    fun bookmarkMovie(movie: EntityMoviesBookmark)
}
