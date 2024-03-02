package com.example.cognizance.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cognizance.ui.screens.BookmarkScreen
import com.example.cognizance.ui.screens.HomeScreen
import com.example.cognizance.ui.screens.MovieListScreen
import com.example.ui.theme.WingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiary
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = NavGraph.Home.route) {
                        composable(NavGraph.Home.route) {
                            HomeScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed,
                                navigateToNowPlayingAction = {
                                    navController.navigate(NavGraph.NowPlaying.route)
                                },
                                bookmarkClickAction = {
                                    navController.navigate(NavGraph.Bookmarks.route)
                                }
                            )
                        }
                        composable(NavGraph.NowPlaying.route) {
                            MovieListScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed,
                                bookmarkClickAction = {
                                    navController.navigate(NavGraph.Bookmarks.route)
                                }
                            )
                        }
                        composable(NavGraph.Bookmarks.route) {
                            BookmarkScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class NavGraph(val route: String) {
    object Home : NavGraph("home")
    object NowPlaying : NavGraph("nowplaying")
    object Bookmarks : NavGraph("bookmark")
}
