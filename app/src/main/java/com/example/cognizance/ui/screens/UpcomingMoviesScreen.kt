package com.example.cognizance.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.MovieListContent
import com.example.cognizance.ui.viewmodel.MovieListViewModel
import com.example.ui.models.WingTopAppBarActionProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun UpcomingMoviesScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onCardClick: (Int) -> Unit,
    onBookmarkClick: () -> Unit
) {
    val movies: LazyPagingItems<Movie> = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val bookmarks by viewModel.bookmarks.collectAsState()

    MovieListContent(
        wingTopAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.upcoming),
            onBackPress = onBackPress,
            actionProps = listOf(
                WingTopAppBarActionProps(
                    actionTitle = stringResource(R.string.bookmarks),
                    onActionClick = onBookmarkClick
                )
            )
        ),
        movies = movies,
        bookmarks = bookmarks,
        onClick = viewModel::onClick,
        onCardClick = onCardClick
    )
}
