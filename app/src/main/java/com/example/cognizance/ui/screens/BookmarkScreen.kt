package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.TMDBCard
import com.example.cognizance.ui.composables.TMDBImage
import com.example.cognizance.ui.models.UiEvents
import com.example.cognizance.ui.viewmodel.BookmarksViewModel
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingScaffold
import com.example.ui.composables.WingSpacer

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    backPressAction: () -> Unit
) {
    val bookmarkedMovies by viewModel.bookmarkedMovies.collectAsState()

    Content(
        bookmarkedMovies = bookmarkedMovies,
        backPressAction = backPressAction,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun Content(
    bookmarkedMovies: List<Movie>,
    backPressAction: () -> Unit,
    onEvent: (UiEvents) -> Unit
) {
    WingScaffold(
        title = stringResource(R.string.bookmarks),
        onBackPress = backPressAction
    ) { paddingValues ->

        if (bookmarkedMovies.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.empty_bookmarks),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(bookmarkedMovies) {
                    MovieRow(
                        movie = it,
                        onBookmarkClick = onEvent
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieRow(
    movie: Movie,
    onBookmarkClick: (UiEvents.OnBookmarkClick) -> Unit
) {
    TMDBCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TMDBImage(url = movie.posterPath)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge
                )
                WingSpacer(height = 2.dp)
                Text(
                    text = "Language: ${movie.originalLanguage}",
                    style = MaterialTheme.typography.labelSmall
                )
                WingSpacer(height = 2.dp)
                Text(
                    text = movie.releaseDate.toDateString(),
                    style = MaterialTheme.typography.labelSmall
                )
                WingSpacer(height = 4.dp)
                Text(
                    text = movie.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick = {
                    onBookmarkClick(UiEvents.OnBookmarkClick(movie.id))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null
                )
            }
        }
    }
}
