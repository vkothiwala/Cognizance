package com.example.cognizance.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cognizance.R
import com.example.ui.composables.WingScaffold

@Composable
fun BookmarkScreen(
    backPressAction: () -> Unit
) {
    WingScaffold(
        title = stringResource(R.string.bookmarks),
        onBackPress = backPressAction
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Text(text = "Bookmarks")
        }
    }
}
