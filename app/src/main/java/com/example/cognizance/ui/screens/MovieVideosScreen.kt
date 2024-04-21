package com.example.cognizance.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.ui.composables.YoutubePlayer
import com.example.cognizance.ui.models.MovieVideosUiState
import com.example.cognizance.ui.viewmodels.MovieVideosViewModel
import com.example.cognizance.utils.movieVideosOf
import com.example.ui.composables.WingEmptyState
import com.example.ui.composables.WingPreview
import com.example.ui.composables.WingProgressIndicator
import com.example.ui.composables.WingScaffold
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps
import com.example.ui.utils.LocalNavController

@Composable
fun MovieVideosScreen(
    viewModel: MovieVideosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    MovieVideosContent(uiState = uiState)
}

@Composable
private fun MovieVideosContent(uiState: MovieVideosUiState) {
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
        if (uiState.isError || uiState.movieVideosByType.isEmpty()) {
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
                    .padding(1.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(uiState.movieVideosByType.size) { index ->
                    VideoCategory(uiState.movieVideosByType.keys.elementAt(index))
                    uiState.movieVideosByType.values.elementAt(index).forEach {
                        VideoTile(
                            label = it.name,
                            onClick = {
                                videoId = it.key
                                playVideo = true
                            }
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

@Composable
private fun VideoCategory(label: String) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun VideoTile(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 1.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun Preview_MovieVideosContent() {
    WingPreview {
        MovieVideosContent(
            uiState = MovieVideosUiState(
                movieVideosByType = mapOf(
                    "Trailer" to movieVideosOf(),
                    "Featurette" to movieVideosOf()
                )
            )
        )
    }
}
