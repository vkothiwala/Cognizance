package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.NavGraph
import com.example.cognizance.ui.composables.MovieRow
import com.example.cognizance.ui.models.BookmarkIconProps
import com.example.cognizance.ui.models.MovieCardClickEvent
import com.example.cognizance.ui.viewmodels.BookmarksViewModel
import com.example.ui.composables.WingEmptyState
import com.example.ui.composables.WingScaffold
import com.example.ui.models.DeviceType
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps
import com.example.ui.utils.LocalDeviceType
import com.example.ui.utils.LocalNavController

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val bookmarkedMovies by viewModel.bookmarkedMovies.collectAsState()

    Content(
        bookmarkedMovies = bookmarkedMovies,
        onClick = viewModel::onClick
    )
}

@Composable
private fun Content(
    bookmarkedMovies: List<Movie>,
    onClick: (MovieCardClickEvent) -> Unit
) {
    val navController = LocalNavController.current
    WingScaffold(
        topAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.bookmarks),
            navigationProps = WingTopAppBarNavigationProps(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        )
    ) { paddingValues ->
        if (bookmarkedMovies.isEmpty()) {
            WingEmptyState(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                message = stringResource(R.string.empty_bookmarks)
            )
        } else {
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
                items(bookmarkedMovies) { movie ->
                    MovieRow(
                        movie = movie,
                        bookmarkIconProps = BookmarkIconProps(
                            isBookmarked = true,
                            onBookmarkClick = {
                                onClick(MovieCardClickEvent.OnBookmarkIconClick(movie.id))
                            }
                        ),
                        onCardClick = {
                            navController.navigate(
                                NavGraph.Details.getRouteWithParam(
                                    movie.id
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
