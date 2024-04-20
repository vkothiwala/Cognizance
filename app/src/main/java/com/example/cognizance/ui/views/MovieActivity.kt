package com.example.cognizance.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cognizance.ui.models.NavGraph
import com.example.cognizance.ui.screens.BookmarkScreen
import com.example.cognizance.ui.screens.HomeScreen
import com.example.cognizance.ui.screens.MediaPlayerScreen
import com.example.cognizance.ui.screens.MovieDetailsScreen
import com.example.cognizance.ui.screens.MovieVideosScreen
import com.example.cognizance.ui.screens.PopularMoviesScreen
import com.example.cognizance.ui.screens.TopRatedMoviesScreen
import com.example.cognizance.ui.screens.UpcomingMoviesScreen
import com.example.cognizance.ui.screens.YoutubePlayerScreen
import com.example.cognizance.utils.LocalNavController
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
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    val navController = rememberNavController()
                    CompositionLocalProvider(LocalNavController provides navController) {
                        NavHost(
                            navController = navController,
                            startDestination = NavGraph.Home.route
                        ) {
                            // Home Screen
                            composable(NavGraph.Home.route) {
                                HomeScreen(
                                    navigateToUpcomingAction = {
                                        navController.navigate(NavGraph.Upcoming.route)
                                    },
                                    navigateToPopularAction = {
                                        navController.navigate(NavGraph.Popular.route)
                                    },
                                    navigateToTopRatedAction = {
                                        navController.navigate(NavGraph.TopRated.route)
                                    },
                                    onBookmarkClick = {
                                        navController.navigate(NavGraph.Bookmarks.route)
                                    }
                                )
                            }
                            // Bookmarks Screen
                            composable(NavGraph.Bookmarks.route) {
                                BookmarkScreen(
                                    onBackPress = onBackPressedDispatcher::onBackPressed,
                                    onCardClick = { movieId ->
                                        navController.navigate(
                                            NavGraph.Details.getRouteWithParam(
                                                movieId
                                            )
                                        )
                                    }
                                )
                            }
                            // Upcoming Screen
                            composable(NavGraph.Upcoming.route) {
                                UpcomingMoviesScreen(
                                    onBackPress = onBackPressedDispatcher::onBackPressed,
                                    onBookmarkClick = {
                                        navController.navigate(NavGraph.Bookmarks.route)
                                    },
                                    onCardClick = { movieId ->
                                        navController.navigate(
                                            NavGraph.Details.getRouteWithParam(
                                                movieId
                                            )
                                        )
                                    }
                                )
                            }
                            // Popular Screen
                            composable(NavGraph.Popular.route) {
                                PopularMoviesScreen(
                                    onBackPress = onBackPressedDispatcher::onBackPressed,
                                    onCardClick = { movieId ->
                                        navController.navigate(
                                            NavGraph.Details.getRouteWithParam(
                                                movieId
                                            )
                                        )
                                    },
                                    onBookmarkClick = {
                                        navController.navigate(NavGraph.Bookmarks.route)
                                    }
                                )
                            }
                            // TopRated Screen
                            composable(NavGraph.TopRated.route) {
                                TopRatedMoviesScreen(
                                    onBackPress = onBackPressedDispatcher::onBackPressed,
                                    onCardClick = { movieId ->
                                        navController.navigate(
                                            NavGraph.Details.getRouteWithParam(
                                                movieId
                                            )
                                        )
                                    },
                                    onBookmarkClick = {
                                        navController.navigate(NavGraph.Bookmarks.route)
                                    }
                                )
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
                                MovieDetailsScreen(
                                    onBackPress = onBackPressedDispatcher::onBackPressed
                                )
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
                            // Youtube video Screen
                            composable(NavGraph.YoutubeVideo.route) { backStackEntry ->
                                backStackEntry.arguments?.getString("videoId")?.let { id ->
                                    YoutubePlayerScreen(id)
                                }
                            }
                            // Media Player
                            composable(NavGraph.MediaPlayer.route) {
                                MediaPlayerScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
