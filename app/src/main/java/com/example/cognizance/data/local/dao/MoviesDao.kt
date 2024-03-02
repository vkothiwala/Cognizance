package com.example.cognizance.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cognizance.data.local.models.EntityMovie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie")
    fun getMovies(): PagingSource<Int, EntityMovie>

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    fun getMovieById(movieId: Int): EntityMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entityMovies: List<EntityMovie>)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'movie'")
    fun resetPrimaryKeyAutoIncrementValue()
}
