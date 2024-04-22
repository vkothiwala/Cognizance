package com.example.cognizance.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.NavGraph
import com.example.cognizance.ui.composables.MovieListContent
import com.example.cognizance.ui.viewmodels.NowPlayingMoviesViewModel
import com.example.ui.models.WingTopAppBarActionProps
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps
import com.example.ui.utils.LocalNavController

@Composable
fun NowPlayingMoviesScreen(
    viewModel: NowPlayingMoviesViewModel = hiltViewModel()
) {
    val movies: LazyPagingItems<Movie> = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val navController = LocalNavController.current

    MovieListContent(
        topAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.now_playing),
            navigationProps = WingTopAppBarNavigationProps(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            ),
            actionProps = listOf(
                WingTopAppBarActionProps(
                    actionTitle = stringResource(R.string.bookmarks),
                    onActionClick = { navController.navigate(NavGraph.Bookmarks.route) }
                )
            )
        ),
        movies = movies,
        bookmarkProp = Pair(bookmarks, viewModel::onClick),
        onCardClick = { movieId ->
            navController.navigate(
                NavGraph.Details.getRouteWithParam(
                    movieId
                )
            )
        }
    )
}
