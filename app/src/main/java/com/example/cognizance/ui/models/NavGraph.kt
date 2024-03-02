package com.example.cognizance.ui.models

sealed class NavGraph(val route: String) {
    object Home : NavGraph("home")
    object Bookmarks : NavGraph("bookmarks")
    object Upcoming : NavGraph("upcoming")
    object Popular : NavGraph("popular")
    object Details : NavGraph("details/{movieId}") {
        fun getRouteWithParam(movieId: Int): String {
            return route.replace("{movieId}", movieId.toString())
        }
    }
}
