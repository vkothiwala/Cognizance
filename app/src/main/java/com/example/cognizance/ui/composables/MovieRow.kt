package com.example.cognizance.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.example.cognizance.ui.models.BookmarkIconProps
import com.example.cognizance.utils.toDateString
import com.example.ui.composables.WingSpacer

@Composable
fun MovieRow(
    movie: Movie,
    bookmarkIconProps: BookmarkIconProps?,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    MovieCard(
        modifier = modifier,
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoviePoster(
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

            bookmarkIconProps?.let { props ->
                IconButton(
                    onClick = props.onBookmarkClick
                ) {
                    Icon(
                        imageVector = props.imageVector,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
