package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.ui.models.SearchMovieUiState
import com.example.cognizance.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchMovieUiState())
    val uiState = _uiState.asStateFlow()

    fun onQueryChange(newQuery: String) {
        if (newQuery != _uiState.value.query) {
            _uiState.update {
                it.copy(query = newQuery)
            }
            if (newQuery.isNotEmpty()) {
                viewModelScope.launch {
                    moviesRepository.searchMovie(newQuery).onSuccess { movies ->
                        _uiState.update {
                            it.copy(movies = movies)
                        }
                    }
                }
            }
        }
    }
}
