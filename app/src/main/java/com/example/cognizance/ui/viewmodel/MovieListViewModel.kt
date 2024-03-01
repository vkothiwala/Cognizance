package com.example.cognizance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies = movieRepository.movies.cachedIn(viewModelScope)
    val bookmarks = movieRepository.bookmarks.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000)
    )

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnBookmarkClick -> {
                viewModelScope.launch {
                    movieRepository.onBookmarkClick(event.movieId)
                }
            }
        }
    }
}

sealed class UiEvent {
    data class OnBookmarkClick(val movieId: Int) : UiEvent()
}
