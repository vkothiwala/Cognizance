package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.ui.composables.BoldTitleTextTile
import com.example.cognizance.ui.composables.MoviePoster
import com.example.cognizance.ui.models.NavGraph
import com.example.cognizance.ui.viewmodels.MovieDetailsViewModel
import com.example.cognizance.utils.LocalNavController
import com.example.cognizance.utils.Response
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingEmptyState
import com.example.ui.composables.WingScaffold
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    if (uiState == null || uiState is Response.Error) {
        WingScaffold(
            topAppBarProps = WingTopAppBarProps(
                title = stringResource(R.string.bookmarks),
                navigationProps = WingTopAppBarNavigationProps(
                    imageVector = Icons.Default.ArrowBack,
                    onClick = onBackPress
                )
            )
        ) { paddingValues ->
            WingEmptyState(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                message = stringResource(R.string.something_wend_wrong)
            )
        }
    } else {
        with((uiState as Response.Success).data) {
            WingScaffold(
                topAppBarProps = WingTopAppBarProps(
                    title = title,
                    navigationProps = WingTopAppBarNavigationProps(
                        imageVector = Icons.Default.ArrowBack,
                        onClick = onBackPress
                    )
                )
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(all = 2.dp)
                ) {
                    item {
                        MoviePoster(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .height(300.dp),
                            url = backdropPath
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .padding(horizontal = 4.dp),
                            onClick = {
                                navController.navigate(
                                    NavGraph.Videos.getRouteWithParam(
                                        movieId = id
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            )
                        ) {
                            Text(text = stringResource(id = R.string.watch_videos))
                        }
                        BoldTitleTextTile(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .padding(top = 4.dp),
                            title = "Overview: ",
                            message = overview,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        BoldTitleTextTile(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 8.dp),
                            title = "Release Date: ",
                            message = releaseDate.toDateString(),
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        BoldTitleTextTile(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 8.dp),
                            title = "Status: ",
                            message = status,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        BoldTitleTextTile(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 8.dp),
                            title = "Votes Rating: ",
                            message = voteAverage.toString(),
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        BoldTitleTextTile(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 8.dp),
                            title = "Runtime: ",
                            message = "$runtime mins",
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        BoldTitleTextTile(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 8.dp),
                            title = "Tagline: ",
                            message = tagline,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
