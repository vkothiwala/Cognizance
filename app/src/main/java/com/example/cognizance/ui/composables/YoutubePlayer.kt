package com.example.cognizance.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayer(videoId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var playerViewStartSeconds by rememberSaveable { mutableStateOf(0f) }
    var isPlayerViewPaused by rememberSaveable { mutableStateOf(false) }

    val youtubePlayerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            initialize(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.apply {
                            loadVideo(videoId, playerViewStartSeconds)
                            if (isPlayerViewPaused) pause()
                        }
                        toggleFullScreen()
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
    }

    DisposableEffect(Unit) {
        onDispose {
            youtubePlayerView.release()
        }
    }

    AndroidView(
        factory = { youtubePlayerView },
        modifier = modifier,
        update = {
            it.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
    )
}
