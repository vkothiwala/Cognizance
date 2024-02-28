package com.example.cognizance.data.mappers

import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie
import com.example.cognizance.domain.models.Movie

fun ApiMovie.toMovie(): Movie {
    return with(this) {
        Movie(
            id = id,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title
        )
    }
}

fun EntityMovie.toMovie(): Movie {
    return with(this) {
        Movie(
            id = id,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title
        )
    }
}
