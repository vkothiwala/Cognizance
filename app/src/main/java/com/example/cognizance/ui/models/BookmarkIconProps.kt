package com.example.cognizance.ui.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star

data class BookmarkIconProps(
    val isBookmarked: Boolean,
    val onBookmarkClick: () -> Unit
) {
    val imageVector = when {
        isBookmarked -> Icons.Filled.Check
        else -> Icons.Filled.Star
    }
}
