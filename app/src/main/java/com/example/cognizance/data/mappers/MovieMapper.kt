package com.example.cognizance.data.mappers

import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.domain.models.Movie

fun EntityMovie.toMovie(): Movie = with(this) {
    Movie(
        id = id,
        originalLanguage = originalLanguage,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun ApiMovie.toMovie(): Movie = with(this) {
    Movie(
        id = id,
        originalLanguage = originalLanguage,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}
