package com.example.cognizance.domain.models

data class MovieDetails(
    val id: Long,
    val title: String,
    val backdropPath: String,
    val overview: String,
    val popularity: Double,
    val tagline: String,
    val status: String,
    val voteAverage: Double,
    val voteCount: Long,
    val runtime: Long,
    val revenue: Long,
    val budget: Long,
    val releaseDate: String
)
