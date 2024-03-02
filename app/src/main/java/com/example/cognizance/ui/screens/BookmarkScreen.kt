package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.MovieRow
import com.example.cognizance.ui.models.UiEvents
import com.example.cognizance.ui.viewmodel.BookmarksViewModel
import com.example.ui.composables.WingScaffold
import com.example.ui.composables.WingSpacer

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    backPressAction: () -> Unit,
    cardClickAction: (Int) -> Unit
) {
    val bookmarkedMovies by viewModel.bookmarkedMovies.collectAsState()

    Content(
        bookmarkedMovies = bookmarkedMovies,
        backPressAction = backPressAction,
        onEvent = viewModel::onEvent,
        onCardClick = cardClickAction
    )
}

@Composable
private fun Content(
    bookmarkedMovies: List<Movie>,
    backPressAction: () -> Unit,
    onEvent: (UiEvents) -> Unit,
    onCardClick: (Int) -> Unit
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
                        isBookmarked = true,
                        onBookmarkClick = onEvent,
                        onCardClick = {
                            onCardClick(it.id)
                        }
                    )
                }
                item {
                    WingSpacer(height = 8.dp)
                }
            }
        }
    }
}
