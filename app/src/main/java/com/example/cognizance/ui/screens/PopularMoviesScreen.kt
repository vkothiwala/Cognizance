package com.example.cognizance.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.MovieListContent
import com.example.cognizance.ui.viewmodel.MovieListViewModel
import com.example.ui.models.WingTopAppBarProps

@Composable
fun PopularMoviesScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onCardClick: (Int) -> Unit
) {
    val movies: LazyPagingItems<Movie> = viewModel.popularMovies.collectAsLazyPagingItems()

    MovieListContent(
        wingTopAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.popular),
            onBackPress = onBackPress
        ),
        movies = movies,
        bookmarks = null,
        onClick = viewModel::onClick,
        onCardClick = onCardClick
    )
}
