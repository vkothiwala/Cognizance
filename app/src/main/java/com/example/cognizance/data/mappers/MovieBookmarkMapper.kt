package com.example.cognizance.data.mappers

import com.example.cognizance.data.models.EntityMoviesBookmark
import com.example.cognizance.domain.models.MovieBookmark

fun EntityMoviesBookmark.toMovieBookmark() = MovieBookmark(
    id = id
)
