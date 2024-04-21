package com.example.cognizance.utils

import com.example.cognizance.domain.models.Movie
import com.example.cognizance.domain.models.MovieVideo
import org.threeten.bp.LocalDate

fun testMovie() = Movie(
    id = 1,
    title = "Angry Birds",
    overview = "Characters from different backgrounds are thrown together when the plane they're travelling on crashes into the Pacific Ocean. A nightmare fight for survival ensues with the air supply running out and dangers creeping in from all sides.",
    originalLanguage = "en-EN",
    posterPath = null,
    releaseDate = LocalDate.now().toString(),
    voteAverage = 7.8f
)

fun movieVideosOf(): List<MovieVideo> {
    return listOf(
        MovieVideo("0", "Video 1", "key 1", "Type"),
        MovieVideo("1", "Video 2", "Key 2", "Type"),
        MovieVideo("2", "Video 3", "Key 3", "Type")
    )
}
