package com.example.cognizance.data.mappers

import com.example.cognizance.data.models.ApiMovie
import com.example.cognizance.data.models.EntityMovie

fun ApiMovie.toEntityMovie(currentPage: Int, totalPages: Int) = EntityMovie(
    id = id,
    originalLanguage = originalLanguage,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    currentPage = currentPage,
    totalPages = totalPages
)
