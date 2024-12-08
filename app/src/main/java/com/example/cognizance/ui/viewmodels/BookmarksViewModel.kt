package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.ui.models.MovieCardClickEvent
import com.example.cognizance.utils.dataOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    val bookmarkedMovies = bookmarksRepository.bookmarks
        .map { movieBookmarks ->
            movieBookmarks.mapNotNull {
                moviesRepository.getMovieById(it.id).dataOrNull()
            }
        }
        .stateIn(
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
