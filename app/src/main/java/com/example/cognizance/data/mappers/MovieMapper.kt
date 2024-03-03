package com.example.cognizance.data.mappers

import com.example.cognizance.data.local.models.EntityMovie
import com.example.cognizance.data.remote.models.ApiMovie
import com.example.cognizance.data.remote.models.ApiMovieDetails
import com.example.cognizance.domain.models.Movie

fun EntityMovie.toMovie(): Movie = Movie(
    id = movieId,
    originalLanguage = originalLanguage,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun ApiMovie.toMovie(): Movie = Movie(
    id = movieId,
    originalLanguage = originalLanguage,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun ApiMovieDetails.toMovie(): Movie = Movie(
    id = id,
    originalLanguage = originalLanguage,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)
