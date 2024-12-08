package com.example.cognizance.ui.models

import com.example.cognizance.domain.models.Movie

data class HomeUiState(
    val isLoading: Boolean = false,
    val upcomingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList()
)
