package com.example.cognizance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cognizance.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val uiState = movieRepository.movies.cachedIn(viewModelScope)

    fun onFavouriteClick(movieId: Int) {
        viewModelScope.launch {
            movieRepository.onFavouriteClick(movieId)
        }
    }
}
