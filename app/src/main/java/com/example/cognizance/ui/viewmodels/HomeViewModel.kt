package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.utils.MovieCategoryType
import com.example.cognizance.utils.onError
import com.example.cognizance.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.getMovies(MovieCategoryType.Upcoming)
                .onSuccess { upcomingMovies ->
                    _uiState.update { it.copy(isLoading = false, upcomingMovies = upcomingMovies) }
                }
                .onError { error ->
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val upcomingMovies: List<Movie> = emptyList()
)
