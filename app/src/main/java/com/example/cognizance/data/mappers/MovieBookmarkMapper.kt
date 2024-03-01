package com.example.cognizance.data.mappers

import com.example.cognizance.data.local.models.EntityMoviesBookmark
import com.example.cognizance.domain.models.MovieBookmark

fun EntityMoviesBookmark.toMovieBookmark() = MovieBookmark(
    id = id
)
