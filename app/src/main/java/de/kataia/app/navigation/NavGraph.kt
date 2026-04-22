package de.kataia.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.kataia.app.ui.screens.HomeScreen
import de.kataia.app.ui.screens.GalleryScreen
import de.kataia.app.ui.screens.ProgressScreen
import de.kataia.app.ui.screens.BadgeScreen
import de.kataia.app.ui.screens.ChallengeDetailScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Gallery.route) {
            GalleryScreen()
        }
        composable(route = Screen.Progress.route) {
            ProgressScreen()
        }
        composable(route = Screen.Badges.route) {
            BadgeScreen()
        }
        composable(route = Screen.ChallengeDetail.route) {
            ChallengeDetailScreen()
        }
    }
}