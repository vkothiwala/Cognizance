package com.example.cognizance.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.TMDBImage
import com.example.cognizance.ui.viewmodel.MovieListViewModel
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingSpacer

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val uiState: LazyPagingItems<Movie> = viewModel.uiState.collectAsLazyPagingItems()

    MovieListContent(
        movies = uiState,
        onFavouriteClick = viewModel::onFavouriteClick
    )
}

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListContent(movies: LazyPagingItems<Movie>, onFavouriteClick: (Int) -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Now playing movies")
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(movies.itemCount) { index ->
                MovieRow(movie = movies[index]!!, onFavouriteClick = {
                    onFavouriteClick(movies[index]!!.id)
                })
            }
            item {
                WingSpacer(height = 8.dp)
            }

            item {
                if (movies.loadState.append is LoadState.Loading) {
                    ProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onFavouriteClick: () -> Unit) = with(movie) {
    Card(
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TMDBImage(url = posterPath)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                WingSpacer(height = 2.dp)
                Text(
                    text = "Language: $originalLanguage",
                    style = MaterialTheme.typography.labelSmall
                )
                WingSpacer(height = 2.dp)
                Text(
                    text = releaseDate.toDateString(),
                    style = MaterialTheme.typography.labelSmall
                )
                WingSpacer(height = 4.dp)
                Text(
                    text = overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onFavouriteClick) {
                Icon(
                    imageVector = if (movie.flag) {
                        Icons.Filled.Check
                    } else {
                        Icons.Filled.Star
                    },
                    contentDescription = null
                )
            }
        }
    }
}
