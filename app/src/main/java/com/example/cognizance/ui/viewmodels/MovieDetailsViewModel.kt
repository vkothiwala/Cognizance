package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.models.MovieDetails
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.utils.Response
import com.example.cognizance.utils.dataOrNull
import com.example.cognizance.utils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState: MutableStateFlow<Response<MovieDetails>?> = MutableStateFlow(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val movieDetails = moviesRepository.getMovieDetails(movieId)
            val videoId = moviesRepository.getMovieVideoId(movieId)
            _uiState.value = movieDetails.map {
                it.copy(videoId = videoId.dataOrNull())
            }
        }
    }
}
