package com.example.cognizance.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class EntityMovie(
    @PrimaryKey var id: Int,
    @ColumnInfo("adult") var adult: Boolean,
    @ColumnInfo("original_language") var originalLanguage: String,
    @ColumnInfo("original_title") var originalTitle: String,
    @ColumnInfo("overview") var overview: String,
    @ColumnInfo("popularity") var popularity: Float,
    @ColumnInfo("poster_path") var posterPath: String?,
    @ColumnInfo("release_date") var releaseDate: String,
    @ColumnInfo("title") var title: String
)

fun ApiMovie.toEntityMovie() = EntityMovie(
    id = id,
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title
)
