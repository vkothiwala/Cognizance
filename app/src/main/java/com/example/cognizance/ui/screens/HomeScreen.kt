package com.example.cognizance.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.NavGraph
import com.example.cognizance.ui.composables.MovieCard
import com.example.cognizance.ui.composables.MoviePoster
import com.example.cognizance.ui.models.HomeUiState
import com.example.cognizance.ui.viewmodels.HomeViewModel
import com.example.ui.composables.WingEmptyState
import com.example.ui.composables.WingProgressIndicator
import com.example.ui.composables.WingScaffold
import com.example.ui.composables.WingSpacer
import com.example.ui.models.WingTopAppBarActionProps
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps
import com.example.ui.utils.LocalNavController

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeContent(uiState = uiState)
}

@Composable
private fun HomeContent(uiState: HomeUiState) {
    val navController = LocalNavController.current
    WingScaffold(
        topAppBarProps = WingTopAppBarProps(
            title = stringResource(R.string.home),
            navigationProps = WingTopAppBarNavigationProps(
                imageVector = Icons.Default.Home,
                onClick = {}
            ),
            actionProps = listOf(
                WingTopAppBarActionProps(
                    actionTitle = stringResource(R.string.bookmarks),
                    onActionClick = {
                        navController.navigate(NavGraph.Bookmarks.route)
                    }
                )
            )
        )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategorySection(
                sectionTitle = stringResource(id = R.string.upcoming),
                movies = uiState.upcomingMovies,
                onViewMoreClick = {
                    navController.navigate(NavGraph.Upcoming.route)
                }
            )
            Divider()
            CategorySection(
                sectionTitle = stringResource(id = R.string.popular),
                movies = uiState.popularMovies,
                onViewMoreClick = {
                    navController.navigate(NavGraph.Popular.route)
                }
            )
            Divider()
            CategorySection(
                sectionTitle = stringResource(id = R.string.top_rated),
                movies = uiState.topRatedMovies,
                onViewMoreClick = {
                    navController.navigate(NavGraph.TopRated.route)
                }
            )
        }
        if (uiState.isLoading) {
            WingProgressIndicator(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun CategorySection(
    sectionTitle: String,
    movies: List<Movie>,
    onViewMoreClick: () -> Unit
) {
    WingSpacer(height = 4.dp)
    SectionTitle(sectionTitle, onViewMoreClick)
    if (movies.isEmpty()) {
        WingEmptyState(
            message = "We are having trouble fetching movies at the moment. Please try again later.",
            modifier = Modifier.padding(8.dp)
        )
    } else {
        MoviesCarousel(movies = movies)
    }
    WingSpacer(height = 4.dp)
}

@Composable
private fun SectionTitle(title: String, onViewMoreClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            modifier = Modifier
                .padding(4.dp)
                .clickable(onClick = onViewMoreClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.view_more),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline
            )
            Icon(
                tint = MaterialTheme.colorScheme.outline,
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun MoviesCarousel(movies: List<Movie>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
private fun MovieCard(movie: Movie) {
    val navController = LocalNavController.current
    MovieCard(
        modifier = Modifier
            .width(142.dp)
            .height(208.dp),
        onClick = {
            navController.navigate(
                NavGraph.Details.getRouteWithParam(
                    movie.id
                )
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MoviePoster(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(160.dp)
                    .height(160.dp),
                url = movie.posterPath
            )
            Text(
                modifier = Modifier.fillMaxSize(),
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                text = movie.title,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
