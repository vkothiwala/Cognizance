package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.BookmarksRepository
import com.example.cognizance.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
    bookmarksRepository: BookmarksRepository
) : BaseMoviesViewModel(bookmarksRepository) {

    val upcomingMovies = moviesRepository.upcomingMovies.cachedIn(viewModelScope)
}
