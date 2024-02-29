package com.example.cognizance.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.composables.TMDBImage
import com.example.cognizance.ui.viewmodel.MovieListViewModel

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val uiState: LazyPagingItems<Movie> = viewModel.uiState.collectAsLazyPagingItems()

    MovieListContent(
        movies = uiState
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
fun MovieListContent(movies: LazyPagingItems<Movie>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Now playing movies")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(movies.itemCount) { movie ->
                MovieRow(movie = movies[movie]!!)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
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
fun MovieRow(movie: Movie) = with(movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .wrapContentHeight(),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TMDBImage(url = posterPath)
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Language: $originalLanguage",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "Releasing on $releaseDate",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = overview,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
