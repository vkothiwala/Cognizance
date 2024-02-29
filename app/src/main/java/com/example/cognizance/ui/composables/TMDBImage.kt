package com.example.cognizance.ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cognizance.R

const val PATH = "https://image.tmdb.org/t/p/original/"

@Composable
fun TMDBImage(modifier: Modifier = Modifier, url: String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("$PATH$url")
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .width(80.dp)
            .height(120.dp)
    )
}
