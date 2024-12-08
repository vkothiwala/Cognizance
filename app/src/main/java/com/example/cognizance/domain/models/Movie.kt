package com.example.cognizance.domain.models

data class Movie(
    var id: Int,
    var title: String,
    var overview: String,
    var originalLanguage: String,
    var posterPath: String?,
    var releaseDate: String,
    var voteAverage: Float
)
