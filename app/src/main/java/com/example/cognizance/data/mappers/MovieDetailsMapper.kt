package com.example.cognizance.data.mappers

import com.example.cognizance.data.remote.models.ApiMovieDetails
import com.example.cognizance.domain.models.MovieDetails

fun ApiMovieDetails.toMovieDetailsMapper() = MovieDetails(
    id = id,
    title = title,
    backdropPath = backdropPath,
    overview = overview,
    popularity = popularity,
    tagline = tagline,
    status = status,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime,
    revenue = revenue,
    budget = budget,
    releaseDate = releaseDate
)
