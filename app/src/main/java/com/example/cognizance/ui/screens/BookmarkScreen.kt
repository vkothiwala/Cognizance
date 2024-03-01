package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cognizance.R
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.viewmodel.BookmarksViewModel
import com.example.ui.composables.WingScaffold

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    backPressAction: () -> Unit
) {
    val bookmarkedMovies by viewModel.bookmarkedMovies.collectAsState()

    Content(
        bookmarkedMovies = bookmarkedMovies,
        backPressAction = backPressAction
    )
}

@Composable
private fun Content(bookmarkedMovies: List<Movie>, backPressAction: () -> Unit) {
    WingScaffold(
        title = stringResource(R.string.bookmarks),
        onBackPress = backPressAction
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            bookmarkedMovies.forEach {
                Text(text = it.title)
            }
        }
    }
}
