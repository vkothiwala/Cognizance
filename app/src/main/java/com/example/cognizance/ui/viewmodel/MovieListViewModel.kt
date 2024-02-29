package com.example.cognizance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    val uiState = movieRepository.movies.cachedIn(viewModelScope)
}
