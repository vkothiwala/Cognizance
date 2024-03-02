package com.example.cognizance.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cognizance.ui.models.NavGraph
import com.example.cognizance.ui.screens.BookmarkScreen
import com.example.cognizance.ui.screens.HomeScreen
import com.example.cognizance.ui.screens.MovieDetailsScreen
import com.example.cognizance.ui.screens.NowPlayingMoviesScreen
import com.example.cognizance.ui.screens.PopularMoviesScreen
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
                    color = MaterialTheme.colorScheme.primary
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = NavGraph.Home.route) {
                        composable(NavGraph.Home.route) {
                            HomeScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed,
                                navigateToNowPlayingAction = {
                                    navController.navigate(NavGraph.NowPlaying.route)
                                },
                                navigateToPopularAction = {
                                    navController.navigate(NavGraph.Popular.route)
                                },
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
                        composable(NavGraph.NowPlaying.route) {
                            NowPlayingMoviesScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed,
                                bookmarkClickAction = {
                                    navController.navigate(NavGraph.Bookmarks.route)
                                },
                                cardClickAction = { movieId ->
                                    navController.navigate(
                                        NavGraph.Details.getRouteWithParam(
                                            movieId
                                        )
                                    )
                                }
                            )
                        }
                        composable(NavGraph.Popular.route) {
                            PopularMoviesScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed,
                                bookmarkClickAction = {
                                    navController.navigate(NavGraph.Bookmarks.route)
                                },
                                cardClickAction = { movieId ->
                                    navController.navigate(
                                        NavGraph.Details.getRouteWithParam(
                                            movieId
                                        )
                                    )
                                }
                            )
                        }
                        composable(
                            NavGraph.Details.route,
                            arguments = listOf(
                                navArgument("movieId") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            MovieDetailsScreen(
                                backPressAction = onBackPressedDispatcher::onBackPressed
                            )
                        }
                    }
                }
            }
        }
    }
}
