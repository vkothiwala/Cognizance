package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.ui.composables.MoviePoster
import com.example.cognizance.ui.viewmodel.MovieDetailsViewModel
import com.example.cognizance.utils.Response
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingScaffold
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.something_wend_wrong),
                    textAlign = TextAlign.Center
                )
            }
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
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(all = 2.dp)
                ) {
                    MoviePoster(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .height(300.dp),
                        url = backdropPath
                    )
                    TextTile(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .padding(top = 4.dp),
                        title = "Overview: ",
                        message = overview,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    TextTile(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 8.dp),
                        title = "Release Date: ",
                        message = releaseDate.toDateString(),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    TextTile(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 8.dp),
                        title = "Status: ",
                        message = status,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    TextTile(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 8.dp),
                        title = "Votes Rating: ",
                        message = voteAverage.toString(),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    TextTile(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 8.dp),
                        title = "Runtime: ",
                        message = "$runtime mins",
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    TextTile(
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

@Composable
fun TextTile(
    title: String,
    message: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = textStyle.copy(fontWeight = FontWeight.Bold).toSpanStyle()
            ) {
                append(title)
            }
            withStyle(
                textStyle.toSpanStyle()
            ) {
                append(message)
            }
        }
    )
}
