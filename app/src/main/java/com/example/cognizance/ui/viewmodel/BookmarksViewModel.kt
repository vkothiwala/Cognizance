package com.example.cognizance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.repositories.BookmarksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    bookmarksRepository: BookmarksRepository
) : ViewModel() {

    private val _bookmarkedMovies = MutableStateFlow(emptyList<Movie>())
    val bookmarkedMovies = _bookmarkedMovies.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarksRepository.getBookmarkedMovies().collect {
                _bookmarkedMovies.value = it
            }
        }
    }
}
