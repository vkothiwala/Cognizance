package com.example.cognizance.data.mappers

import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.remote.models.ApiMovie

fun ApiMovie.toEntityMovie(currentPage: Int) = EntityMovie(
    movieId = movieId,
    originalLanguage = originalLanguage,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    currentPage = currentPage
)
