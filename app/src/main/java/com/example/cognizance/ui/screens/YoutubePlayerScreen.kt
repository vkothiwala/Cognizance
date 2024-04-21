package com.example.cognizance.ui.screens

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.cognizance.ui.composables.LockScreenOrientation
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerScreen(
    videoId: String,
    modifier: Modifier = Modifier,
    shouldViewInLandscape: Boolean = true
) {
    var playerViewStartSeconds by rememberSaveable { mutableStateOf(0f) }
    var isPlayerViewPaused by rememberSaveable { mutableStateOf(false) }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    if (shouldViewInLandscape) {
        LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                enableAutomaticInitialization = false
                initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.apply {
                                loadVideo(videoId, playerViewStartSeconds)
                                if (isPlayerViewPaused) pause()
                            }
                        }

                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                            super.onCurrentSecond(youTubePlayer, second)
                            playerViewStartSeconds = second
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            super.onStateChange(youTubePlayer, state)
                            when (state) {
                                PlayerConstants.PlayerState.PAUSED -> isPlayerViewPaused = true
                                PlayerConstants.PlayerState.PLAYING -> isPlayerViewPaused = false
                                else -> Unit
                            }
                        }
                    }
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        update = { view ->
            view.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        },
        onRelease = { view ->
            lifecycleOwner.lifecycle.removeObserver(view)
            view.release()
        }
    )
}
