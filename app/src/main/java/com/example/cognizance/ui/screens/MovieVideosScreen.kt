package com.example.cognizance.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.ui.composables.YoutubePlayer
import com.example.cognizance.ui.models.MovieVideosUiState
import com.example.cognizance.ui.viewmodels.MovieVideosViewModel
import com.example.cognizance.utils.LocalNavController
import com.example.ui.composables.WingCard
import com.example.ui.composables.WingEmptyState
import com.example.ui.composables.WingProgressIndicator
import com.example.ui.composables.WingScaffold
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun MovieVideosScreen(
    viewModel: MovieVideosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    MovieVideosContent(uiState = uiState)
}

@Composable
fun MovieVideosContent(uiState: MovieVideosUiState) {
    val navController = LocalNavController.current
    var videoId by rememberSaveable { mutableStateOf("") }
    var playVideo by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        when {
            playVideo -> playVideo = false
            else -> navController.navigateUp()
        }
    }

    WingScaffold(
        topAppBarProps = WingTopAppBarProps(
            title = "Movie Videos",
            navigationProps = WingTopAppBarNavigationProps(
                imageVector = Icons.Default.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        )
    ) { paddingValue ->
        if (uiState.isError || uiState.movieVideos.isEmpty()) {
            WingEmptyState(
                message = "No Movie Videos Found!",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
                    .padding(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(uiState.movieVideos) { movieVideo ->
                    WingCard(
                        modifier = Modifier
                            .clickable {
                                videoId = movieVideo.key
                                playVideo = true
                            }
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = movieVideo.name,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
    if (uiState.isLoading) {
        WingProgressIndicator()
    }
    if (playVideo) {
        Box(modifier = Modifier.fillMaxSize()) {
            YoutubePlayer(videoId = videoId)
        }
    }
}
