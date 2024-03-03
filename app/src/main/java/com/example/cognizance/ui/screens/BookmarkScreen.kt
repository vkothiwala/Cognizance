package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.MovieRow
import com.example.cognizance.ui.models.BookmarkIconProps
import com.example.cognizance.ui.models.MovieCardClickEvent
import com.example.cognizance.ui.viewmodels.BookmarksViewModel
import com.example.ui.composables.WingEmptyState
import com.example.ui.composables.WingScaffold
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onCardClick: (Int) -> Unit
) {
    val bookmarkedMovies by viewModel.bookmarkedMovies.collectAsState()

    Content(
        bookmarkedMovies = bookmarkedMovies,
        onBackPress = onBackPress,
        onCardClick = onCardClick,
        onClick = viewModel::onClick
    )
}

@Composable
private fun Content(
    bookmarkedMovies: List<Movie>,
    onBackPress: () -> Unit,
    onCardClick: (Int) -> Unit,
    onClick: (MovieCardClickEvent) -> Unit
) {
    WingScaffold(
        topAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.bookmarks),
            navigationProps = WingTopAppBarNavigationProps(
                imageVector = Icons.Default.ArrowBack,
                onClick = onBackPress
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
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookmarkedMovies) {
                    MovieRow(
                        movie = it,
                        bookmarkIconProps = BookmarkIconProps(
                            isBookmarked = true,
                            onBookmarkClick = {
                                onClick(MovieCardClickEvent.OnBookmarkIconClick(it.id))
                            }
                        ),
                        onCardClick = {
                            onCardClick(it.id)
                        }
                    )
                }
            }
        }
    }
}
