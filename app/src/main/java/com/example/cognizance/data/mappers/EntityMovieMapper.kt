package com.example.cognizance.data.mappers

import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie

fun ApiMovie.toEntityMovie() = EntityMovie(
    id = id,
    originalLanguage = originalLanguage,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)
