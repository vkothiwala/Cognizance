package com.example.cognizance.domain.models

data class Movie(
    var id: Int,
    var originalLanguage: String,
    var originalTitle: String,
    var overview: String,
    var popularity: Float,
    var posterPath: String?,
    var releaseDate: String,
    var title: String
)
