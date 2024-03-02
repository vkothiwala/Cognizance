package com.example.cognizance.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cognizance.domain.models.Movie
import com.example.cognizance.ui.models.UiEvents
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingSpacer

@Composable
fun MovieRow(
    movie: Movie,
    isBookmarked: Boolean,
    onBookmarkClick: (UiEvents.OnBookmarkClick) -> Unit,
    onCardClick: () -> Unit
) {
    TMDBCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TMDBImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(90.dp)
                    .height(90.dp),
                url = movie.posterPath
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge
                )
                WingSpacer(height = 2.dp)
                Text(
                    text = movie.releaseDate.toDateString(),
                    style = MaterialTheme.typography.labelSmall
                )
                WingSpacer(height = 4.dp)
                Text(
                    text = movie.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick = { onBookmarkClick(UiEvents.OnBookmarkClick(movie.id)) }
            ) {
                Icon(
                    imageVector = when {
                        isBookmarked -> Icons.Filled.Check
                        else -> Icons.Filled.Star
                    },
                    contentDescription = null
                )
            }
        }
    }
}
