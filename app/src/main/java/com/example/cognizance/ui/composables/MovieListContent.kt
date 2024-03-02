package com.example.cognizance.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.ui.models.UiEvents
import com.example.ui.composables.WingScaffold
import com.example.ui.composables.WingSpacer

@Composable
fun MovieListContent(
    title: String,
    movies: LazyPagingItems<Movie>,
    bookmarks: List<MovieBookmark>,
    onEvent: (UiEvents) -> Unit,
    onBackPress: () -> Unit,
    bookmarkClickAction: () -> Unit,
    cardClickAction: (Int) -> Unit
) {
    WingScaffold(
        title = title,
        onBackPress = onBackPress,
        actions = {
            AppBarActions(
                bookmarkClickAction = bookmarkClickAction
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(movies.itemCount) { index ->
                movies[index]?.let { movie ->
                    MovieRow(
                        movie = movie,
                        isBookmarked = bookmarks.contains(MovieBookmark(movie.id)),
                        onBookmarkClick = onEvent,
                        onCardClick = {
                            cardClickAction(movie.id)
                        }
                    )
                }
            }
            item {
                WingSpacer(height = 8.dp)
            }

            if (movies.loadState.append is LoadState.Loading) {
                item {
                    ProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}
