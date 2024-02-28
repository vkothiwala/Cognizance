package com.example.cognizance.data.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cognizance.data.models.EntityMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<EntityMovie>>

    @Insert
    fun insertAll(entityMovies: List<EntityMovie>)

    @Query("DELETE FROM movie")
    fun deleteAll()
}
