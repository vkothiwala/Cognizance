package com.example.cognizance.ui.models

sealed class NavGraph(val route: String) {
    object Home : NavGraph("home")
    object Bookmarks : NavGraph("bookmarks")
    object NowPlaying : NavGraph("nowplaying")
    object Popular : NavGraph("popular")
}
