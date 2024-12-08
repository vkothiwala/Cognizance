package com.example.cognizance.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.cognizance.R

@OptIn(UnstableApi::class)
@Composable
fun MediaPlayerScreen(
    modifier: Modifier = Modifier,
    mediaUrl: String = stringResource(id = R.string.media_url_mp3)
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var startPosition by rememberSaveable { mutableStateOf(0L) }
    var isPaused by rememberSaveable { mutableStateOf(false) }

    val exoPlayer = ExoPlayer.Builder(context).build()

    // Manage lifecycle events
    DisposableEffect(lifecycleOwner.lifecycle) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    exoPlayer.setMediaItem(
                        MediaItem.fromUri(mediaUrl),
                        startPosition
                    )
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }

                Lifecycle.Event.ON_PAUSE -> {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                        isPaused = true
                    }
                }

                Lifecycle.Event.ON_START -> {
                    if (isPaused) {
                        exoPlayer.play()
                    }
                }

                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            startPosition = exoPlayer.currentPosition
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    PlayerView(exoPlayer, modifier.fillMaxSize())
}

@Composable
private fun PlayerView(playerImpl: Player, modifier: Modifier = Modifier) {
    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = playerImpl
            }
        },
        modifier = modifier
    )
}
