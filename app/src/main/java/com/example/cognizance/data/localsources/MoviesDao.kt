package com.example.cognizance.data.localsources

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cognizance.data.models.EntityMovie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie")
    fun getMovies(): PagingSource<Int, EntityMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entityMovies: List<EntityMovie>)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'movie'")
    fun resetPrimaryKeyAutoIncrementValue()
}
