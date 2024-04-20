package com.example.cognizance.ui.models

sealed class NavGraph(val route: String) {
    object Home : NavGraph("home")
    object Bookmarks : NavGraph("bookmarks")
    object Upcoming : NavGraph("upcoming")
    object Popular : NavGraph("popular")
    object TopRated : NavGraph("topRated")
    object MediaPlayer : NavGraph("mediaPlayer")
    object Details : NavGraph("details/{movieId}") {
        fun getRouteWithParam(movieId: Int): String {
            return route.replace("{movieId}", movieId.toString())
        }
    }

    object Videos : NavGraph("videos/{movieId}") {
        fun getRouteWithParam(movieId: Int): String {
            return route.replace("{movieId}", movieId.toString())
        }
    }

    object YoutubeVideo : NavGraph("video/{videoId}") {
        fun getRouteWithParam(videoId: String): String {
            return route.replace("{videoId}", videoId)
        }
    }
}
