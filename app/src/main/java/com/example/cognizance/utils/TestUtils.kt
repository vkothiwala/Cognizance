package com.example.cognizance.utils

import com.example.cognizance.domain.models.Movie
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
