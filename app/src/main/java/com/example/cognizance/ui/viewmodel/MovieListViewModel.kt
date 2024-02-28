package com.example.cognizance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.repositories.MovieRepository
import com.example.cognizance.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.movies.collect { movies ->
                _uiState.update {
                    it.copy(movies = movies)
                }
            }
        }
        refresh()
    }

    fun refresh() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (movieRepository.getMovies()) {
                is Response.Success -> _uiState.update {
                    it.copy(isLoading = false)
                }

                is Response.Error -> _uiState.update {
                    it.copy(isLoading = false, showError = true)
                }
            }
        }
    }

    fun onDialogDismiss() {
        _uiState.update {
            it.copy(showError = false)
        }
    }
}

data class UiState(
    val isLoading: Boolean = true,
    val movies: List<Movie> = emptyList(),
    val showError: Boolean = false
)
