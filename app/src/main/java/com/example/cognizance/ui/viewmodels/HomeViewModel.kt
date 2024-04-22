package com.example.cognizance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognizance.domain.models.MovieCategoryType
import com.example.cognizance.domain.repositories.MoviesRepository
import com.example.cognizance.ui.models.HomeUiState
import com.example.cognizance.utils.onError
import com.example.cognizance.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
            val upcomingMoviesResponse = async {
                moviesRepository.getMovies(MovieCategoryType.Upcoming)
            }
            val popularMoviesResponse = async {
                moviesRepository.getMovies(MovieCategoryType.Popular)
            }
            val topRatedMoviesResponse = async {
                moviesRepository.getMovies(MovieCategoryType.TopRated)
            }
            val nowPlayingMoviesResponse = async {
                moviesRepository.getMovies(MovieCategoryType.NowPlaying)
            }
            upcomingMoviesResponse.await()
                .onSuccess { upcomingMovies ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            upcomingMovies = upcomingMovies
                        )
                    }
                }
                .onError {
                    _uiState.update { it.copy(isLoading = false) }
                }

            popularMoviesResponse.await()
                .onSuccess { popularMovies ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            popularMovies = popularMovies
                        )
                    }
                }
                .onError {
                    _uiState.update { it.copy(isLoading = false) }
                }

            topRatedMoviesResponse.await()
                .onSuccess { topRatedMovies ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            topRatedMovies = topRatedMovies
                        )
                    }
                }
                .onError {
                    _uiState.update { it.copy(isLoading = false) }
                }

            nowPlayingMoviesResponse.await()
                .onSuccess { nowPlayingMovies ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            nowPlayingMovies = nowPlayingMovies
                        )
                    }
                }
                .onError {
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }
}
