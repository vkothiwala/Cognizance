package com.example.cognizance.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.ui.models.UiEvents
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingScaffold
import com.example.ui.composables.WingSpacer

@Composable
fun MovieListContent(
    title: String,
    movies: LazyPagingItems<Movie>,
    bookmarks: List<MovieBookmark>,
    onEvent: (UiEvents) -> Unit,
    onBackPress: () -> Unit,
    bookmarkClickAction: () -> Unit
) {
    WingScaffold(
        title = title,
        onBackPress = onBackPress,
        actions = {
            TMDBActions(
                bookmarkClickAction = bookmarkClickAction
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(movies.itemCount) { index ->
                movies[index]?.let { movie ->
                    MovieRow(
                        movie = movie,
                        bookmarks = bookmarks,
                        onBookmarkClick = onEvent
                    )
                }
            }
            item {
                WingSpacer(height = 8.dp)
            }

            if (movies.loadState.append is LoadState.Loading) {
                item {
                    ProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MovieRow(
    movie: Movie,
    bookmarks: List<MovieBookmark>,
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

            val isBookmarked = bookmarks.contains(MovieBookmark(movie.id))
            val bookmarkVector = if (isBookmarked) {
                Icons.Filled.Check
            } else {
                Icons.Filled.Star
            }
            IconButton(
                onClick = {
                    onBookmarkClick(UiEvents.OnBookmarkClick(movie.id))
                }
            ) {
                Icon(
                    imageVector = bookmarkVector,
                    contentDescription = null
                )
            }
        }
    }
}
