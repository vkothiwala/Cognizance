package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.ui.models.MovieCardClickEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    val upcomingMovies = moviesRepository.upcomingMovies.cachedIn(viewModelScope)
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
