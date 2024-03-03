package com.example.cognizance.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.MovieListContent
import com.example.cognizance.ui.viewmodels.TopRatedMoviesViewModel
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun TopRatedMoviesScreen(
    viewModel: TopRatedMoviesViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onCardClick: (Int) -> Unit
) {
    val movies: LazyPagingItems<Movie> = viewModel.topRatedMovies.collectAsLazyPagingItems()

    MovieListContent(
        topAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.top_rated),
            navigationProps = WingTopAppBarNavigationProps(
                imageVector = Icons.Default.ArrowBack,
                onClick = onBackPress
            )
        ),
        movies = movies,
        bookmarkProp = null,
        onCardClick = onCardClick
    )
}
