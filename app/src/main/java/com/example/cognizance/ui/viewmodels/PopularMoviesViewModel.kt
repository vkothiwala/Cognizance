package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    val popularMovies = moviesRepository.popularMovies.cachedIn(viewModelScope)
}
