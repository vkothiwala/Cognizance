package com.example.cognizance.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class EntityMovie(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo("movie_id") var movieId: Int,
    @ColumnInfo("title") var title: String,
    @ColumnInfo("overview") var overview: String,
    @ColumnInfo("original_language") var originalLanguage: String,
    @ColumnInfo("poster_path") var posterPath: String?,
    @ColumnInfo("release_date") var releaseDate: String,
    @ColumnInfo("vote_average") var voteAverage: Float,
    @ColumnInfo("page") var currentPage: Int
)
