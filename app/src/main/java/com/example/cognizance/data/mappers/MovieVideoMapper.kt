package com.example.cognizance.data.mappers

import com.example.cognizance.data.remote.models.ApiMovieVideos
import com.example.cognizance.domain.models.MovieVideo

fun ApiMovieVideos.MovieVideo.toMovieVideo(): MovieVideo = MovieVideo(
    id = id,
    name = name,
    key = key
)
