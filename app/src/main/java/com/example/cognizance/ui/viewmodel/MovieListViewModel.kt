package com.example.cognizance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.ui.models.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val nowPlayingMovies = moviesRepository.nowPlayingMovies.cachedIn(viewModelScope)
    val popularMovies = moviesRepository.popularMovies.cachedIn(viewModelScope)
    val bookmarks = bookmarksRepository.bookmarks.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(2000)
    )

    fun onEvent(event: UiEvents) {
        when (event) {
            is UiEvents.OnBookmarkClick -> {
                viewModelScope.launch {
                    bookmarksRepository.onBookmarkClick(event.movieId)
                }
            }
        }
    }
}
