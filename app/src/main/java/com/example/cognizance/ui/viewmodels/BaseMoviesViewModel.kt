package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.ui.models.MovieCardClickEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class BaseMoviesViewModel(
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val bookmarks = bookmarksRepository.bookmarks.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(2000)
    )

    fun onClick(event: MovieCardClickEvent) {
        when (event) {
            is MovieCardClickEvent.OnBookmarkIconClick -> {
                viewModelScope.launch {
                    bookmarksRepository.onBookmarkClick(event.movieId)
                }
            }
        }
    }
}
