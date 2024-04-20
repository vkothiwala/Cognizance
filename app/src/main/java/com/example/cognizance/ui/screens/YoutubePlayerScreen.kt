package com.example.cognizance.ui.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerScreen(videoId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val window = context.findActivity()?.window
    var startSeconds by rememberSaveable { mutableStateOf(0f) }
    var isPaused by rememberSaveable { mutableStateOf(false) }
    val currentOrientation = LocalConfiguration.current.orientation

    val youtubePlayer = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            initialize(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.apply {
                            toggleFullScreen()
                            loadVideo(videoId, startSeconds)
                            if (isPaused) pause()
                        }
                    }

                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                        super.onCurrentSecond(youTubePlayer, second)
                        startSeconds = second
                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)
                        when (state) {
                            PlayerConstants.PlayerState.PAUSED -> isPaused = true
                            PlayerConstants.PlayerState.PLAYING -> isPaused = false
                            else -> Unit
                        }
                    }
                }
            )
        }
    }

    DisposableEffect(Unit) {
        if (currentOrientation == ORIENTATION_LANDSCAPE && window != null) {
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            insetsController.apply {
                hide(WindowInsetsCompat.Type.statusBars())
                hide(WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            onDispose {
                insetsController.apply {
                    show(WindowInsetsCompat.Type.statusBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }
                youtubePlayer.release()
            }
        } else {
            onDispose {
                youtubePlayer.release()
            }
        }
    }

    AndroidView(
        factory = { youtubePlayer },
        modifier = modifier.fillMaxWidth()
    )
}

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
