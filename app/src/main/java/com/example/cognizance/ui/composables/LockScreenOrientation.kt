package com.example.cognizance.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.example.cognizance.utils.findActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        systemUiController.isStatusBarVisible = false // Status bar
        systemUiController.isNavigationBarVisible = false // Navigation bar
        systemUiController.isSystemBarsVisible = false // Status & Navigation bars

        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
            systemUiController.isStatusBarVisible = true // Status bar
            systemUiController.isNavigationBarVisible = true // Navigation bar
            systemUiController.isSystemBarsVisible = true // Status & Navigation bars
        }
    }
}
