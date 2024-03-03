package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    val topRatedMovies = moviesRepository.topRatedMovies.cachedIn(viewModelScope)
}
