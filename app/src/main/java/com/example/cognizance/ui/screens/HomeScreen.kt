package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cognizance.R
import com.example.cognizance.ui.composables.MovieCard
import com.example.ui.composables.WingScaffold
import com.example.ui.models.WingTopAppBarActionProps
import com.example.ui.models.WingTopAppBarNavigationProps
import com.example.ui.models.WingTopAppBarProps

@Composable
fun HomeScreen(
    navigateToUpcomingAction: () -> Unit,
    navigateToPopularAction: () -> Unit,
    navigateToTopRatedAction: () -> Unit,
    onBookmarkClick: () -> Unit
) {
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
                    onActionClick = onBookmarkClick
                )
            )
        )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HomeCard(
                title = stringResource(R.string.popular),
                onClick = navigateToPopularAction
            )
            HomeCard(
                title = stringResource(R.string.upcoming),
                onClick = navigateToUpcomingAction
            )
            HomeCard(
                title = stringResource(R.string.top_rated),
                onClick = navigateToTopRatedAction
            )
        }
    }
}

@Composable
private fun HomeCard(title: String, onClick: () -> Unit) {
    MovieCard(
        modifier = Modifier.fillMaxWidth(),
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
