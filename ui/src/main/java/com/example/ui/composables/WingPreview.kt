package com.example.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.WingTheme
import com.example.ui.utils.LocalNavController

@Composable
fun WingPreview(content: @Composable () -> Unit) {
    WingTheme {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavController provides navController) {
            content()
        }
    }
}
