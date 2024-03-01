package com.example.cognizance.ui.models

sealed class UiEvents {
    data class OnBookmarkClick(val movieId: Int) : UiEvents()
}
