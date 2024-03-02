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
import com.example.cognizance.ui.models.BookmarkIconProps
import com.example.cognizance.ui.models.MovieCardClickEvent
import com.example.ui.composables.AppBarActions
import com.example.ui.composables.WingScaffold
import com.example.ui.composables.WingSpacer
import com.example.ui.models.TopAppBarProps

@Composable
fun MovieListContent(
    topAppBarProps: TopAppBarProps,
    movies: LazyPagingItems<Movie>,
    bookmarks: List<MovieBookmark>?,
    onClick: (MovieCardClickEvent) -> Unit,
    onCardClick: (Int) -> Unit
) {
    WingScaffold(
        title = topAppBarProps.title,
        onBackPress = topAppBarProps.onBackPress,
        actions = {
            AppBarActions(
                actionProps = topAppBarProps.actionProps
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(movies.itemCount) { index ->
                movies[index]?.let { movie ->
                    MovieRow(
                        movie = movie,
                        bookmarkIconProps = bookmarks?.let {
                            BookmarkIconProps(
                                isBookmarked = it.contains(MovieBookmark(movie.id)),
                                onBookmarkClick = {
                                    onClick(MovieCardClickEvent.OnBookmarkIconClick(movie.id))
                                }
                            )
                        },
                        onCardClick = {
                            onCardClick(movie.id)
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
