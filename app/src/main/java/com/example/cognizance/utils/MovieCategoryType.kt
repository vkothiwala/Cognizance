package com.example.cognizance.utils

sealed class MovieCategoryType {
    object Upcoming : MovieCategoryType()
    object Popular : MovieCategoryType()
    object TopRated : MovieCategoryType()
}
