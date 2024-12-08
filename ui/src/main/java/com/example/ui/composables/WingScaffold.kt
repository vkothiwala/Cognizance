package com.example.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.models.WingTopAppBarProps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WingScaffold(
    topAppBarProps: WingTopAppBarProps,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WingTopAppbar(
                topAppBarProps = topAppBarProps
            )
        },
        content = content
    )
}
