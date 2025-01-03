@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.example.cognizance.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cognizance.ui.NavGraph
import com.example.cognizance.ui.screens.BookmarkScreen
import com.example.cognizance.ui.screens.HomeScreen
import com.example.cognizance.ui.screens.MediaPlayerScreen
import com.example.cognizance.ui.screens.MovieDetailsScreen
import com.example.cognizance.ui.screens.MovieVideosScreen
import com.example.cognizance.ui.screens.NowPlayingMoviesScreen
import com.example.cognizance.ui.screens.PopularMoviesScreen
import com.example.cognizance.ui.screens.TopRatedMoviesScreen
import com.example.cognizance.ui.screens.UpcomingMoviesScreen
import com.example.cognizance.ui.screens.YoutubePlayerScreen
import com.example.ui.models.DeviceType
import com.example.ui.theme.WingTheme
import com.example.ui.utils.LocalDeviceType
import com.example.ui.utils.LocalNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    val navController = rememberNavController()
                    val sizeClass = calculateWindowSizeClass(activity = this)

                    val deviceType = when (sizeClass.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> DeviceType.PHONE
                        WindowWidthSizeClass.Medium -> DeviceType.TABLET
                        WindowWidthSizeClass.Expanded -> DeviceType.TABLET
                        else -> DeviceType.PHONE
                    }
                    CompositionLocalProvider(
                        LocalNavController provides navController,
                        LocalDeviceType provides deviceType
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NavGraph.Home.route
                        ) {
                            // Home Screen
                            composable(NavGraph.Home.route) {
                                HomeScreen()
                            }
                            // Bookmarks Screen
                            composable(NavGraph.Bookmarks.route) {
                                BookmarkScreen()
                            }
                            // Upcoming Screen
                            composable(NavGraph.Upcoming.route) {
                                UpcomingMoviesScreen()
                            }
                            // Popular Screen
                            composable(NavGraph.Popular.route) {
                                PopularMoviesScreen()
                            }
                            // TopRated Screen
                            composable(NavGraph.TopRated.route) {
                                TopRatedMoviesScreen()
                            }
                            // NowPlaying Screen
                            composable(NavGraph.NowPlaying.route) {
                                NowPlayingMoviesScreen()
                            }
                            // Movie Details Screen
                            composable(
                                NavGraph.Details.route,
                                arguments = listOf(
                                    navArgument("movieId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                MovieDetailsScreen()
                            }
                            // Movie videos Screen
                            composable(
                                route = NavGraph.Videos.route,
                                arguments = listOf(
                                    navArgument("movieId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                MovieVideosScreen()
                            }
                            // Media Player
                            composable(NavGraph.MediaPlayer.route) {
                                MediaPlayerScreen()
                            }
                            // Youtube video Screen
                            composable(NavGraph.YoutubePlayer.route) { backStackEntry ->
                                backStackEntry.arguments?.getString("videoId")?.let { id ->
                                    YoutubePlayerScreen(id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
