package com.example.cognizance.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.ui.composables.WingCard
import com.example.ui.theme.WingTheme

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    WingCard(
        modifier = modifier.clickable { onClick() }
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun Preview_MovieCard_Dark_Mode() {
    WingTheme {
        MovieCard {
            Text(modifier = Modifier.padding(8.dp), text = "Movie card Preview")
        }
    }
}
