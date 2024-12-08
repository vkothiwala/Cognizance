package com.example.cognizance.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieBookmark
import com.example.cognizance.ui.models.BookmarkIconProps
import com.example.cognizance.ui.models.MovieCardClickEvent
import com.example.ui.composables.WingProgressIndicator
import com.example.ui.composables.WingScaffold
import com.example.ui.models.DeviceType
import com.example.ui.models.WingTopAppBarProps
import com.example.ui.utils.LocalDeviceType

@Composable
fun MovieListContent(
    topAppBarProps: WingTopAppBarProps,
    movies: LazyPagingItems<Movie>,
    bookmarkProp: Pair<List<MovieBookmark>, (MovieCardClickEvent) -> Unit>?,
    onCardClick: (Int) -> Unit
) {
    WingScaffold(
        topAppBarProps = topAppBarProps
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(
                count = when (LocalDeviceType.current) {
                    DeviceType.TABLET -> 2
                    DeviceType.PHONE -> 1
                }
            ),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies.itemCount) { index ->
                movies[index]?.let { movie ->
                    MovieRow(
                        movie = movie,
                        bookmarkIconProps = bookmarkProp?.let {
                            val bookmarks = it.first
                            val onClick = it.second
                            BookmarkIconProps(
                                isBookmarked = bookmarks.contains(MovieBookmark(movie.id)),
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
            if (movies.loadState.append is LoadState.Loading) {
                item {
                    WingProgressIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
