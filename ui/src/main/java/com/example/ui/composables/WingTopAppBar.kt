package com.example.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ui.models.WingTopAppBarProps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WingTopAppbar(
    topAppBarProps: WingTopAppBarProps,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = topAppBarProps.title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            topAppBarProps.navigationProps?.let { props ->
                IconButton(onClick = props.onClick) {
                    Icon(
                        imageVector = props.imageVector,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            WingTopAppBarActions(
                actionProps = topAppBarProps.actionProps
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
