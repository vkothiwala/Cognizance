package com.example.cognizance.data.mappers

import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.domain.models.Movie

fun EntityMovie.toMovie(flag: Boolean = false): Movie = with(this) {
    Movie(
        id = movieId,
        originalLanguage = originalLanguage,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        flag = false
    )
}

fun ApiMovie.toMovie(flag: Boolean = false): Movie = with(this) {
    Movie(
        id = movieId,
        originalLanguage = originalLanguage,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        flag = flag
    )
}
