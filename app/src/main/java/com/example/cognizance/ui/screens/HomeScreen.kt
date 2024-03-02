package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cognizance.R
import com.example.cognizance.ui.composables.TMDBActions
import com.example.cognizance.ui.composables.TMDBCard
import com.example.ui.composables.WingScaffold

@Composable
fun HomeScreen(
    backPressAction: () -> Unit,
    navigateToNowPlayingAction: () -> Unit,
    bookmarkClickAction: () -> Unit
) {
    WingScaffold(
        title = stringResource(R.string.home),
        onBackPress = backPressAction,
        actions = {
            TMDBActions(
                bookmarkClickAction = bookmarkClickAction
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            HomeCard(
                title = stringResource(R.string.now_playing),
                onClick = navigateToNowPlayingAction
            )
        }
    }
}

@Composable
fun HomeCard(title: String, onClick: () -> Unit) {
    TMDBCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
