package com.example.cognizance.domain.models

sealed class MovieCategoryType {
    object Upcoming : MovieCategoryType()
    object Popular : MovieCategoryType()
    object TopRated : MovieCategoryType()
    object NowPlaying : MovieCategoryType()
}
