package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.models.MovieVideo
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.utils.onError
import com.example.cognizance.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVideosViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow(MovieVideosUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.getMovieVideos(movieId)
                .onSuccess { movieVideos ->
                    _uiState.update {
                        it.copy(isLoading = false, movieVideos = movieVideos)
                    }
                }
                .onError {
                    _uiState.update {
                        it.copy(isLoading = false, isError = true)
                    }
                }
        }
    }
}

data class MovieVideosUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val movieVideos: List<MovieVideo> = emptyList()
)
