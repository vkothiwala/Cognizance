package com.example.cognizance.ui.models

import com.example.cognizance.domain.models.MovieVideo

data class MovieVideosUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val movieVideosByType: Map<String, List<MovieVideo>> = emptyMap()
)
