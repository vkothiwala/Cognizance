package com.example.cognizance.ui.models

import com.example.cognizance.domain.models.Movie

data class SearchMovieUiState(
    val query: String = "",
    val movies: List<Movie> = emptyList()
)
