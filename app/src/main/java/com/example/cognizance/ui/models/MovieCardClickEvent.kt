package com.example.cognizance.ui.models

sealed class MovieCardClickEvent {
    data class OnBookmarkIconClick(val movieId: Int) : MovieCardClickEvent()
}
