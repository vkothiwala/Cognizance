package com.example.cognizance.ui.composables

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cognizance.R

const val PATH = "https://image.tmdb.org/t/p/original/"

@Composable
fun MoviePoster(modifier: Modifier = Modifier, url: String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("$PATH$url")
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.ic_launcher_foreground),
        fallback = painterResource(R.drawable.ic_launcher_foreground),
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.background(MaterialTheme.colorScheme.outlineVariant)
    )
}
