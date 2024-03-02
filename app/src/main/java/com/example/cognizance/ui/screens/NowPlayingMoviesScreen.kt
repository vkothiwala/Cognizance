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

@Composable
fun NowPlayingMoviesScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    backPressAction: () -> Unit,
    bookmarkClickAction: () -> Unit
) {
    val movies: LazyPagingItems<Movie> = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val bookmarks by viewModel.bookmarks.collectAsState()

    MovieListContent(
        title = stringResource(R.string.now_playing),
        movies = movies,
        bookmarks = bookmarks,
        onEvent = viewModel::onEvent,
        onBackPress = backPressAction,
        bookmarkClickAction = bookmarkClickAction
    )
}
