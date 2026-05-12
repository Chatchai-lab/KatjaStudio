package de.kataia.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.kataia.app.data.UserPreferencesRepository
import de.kataia.app.data.ChallengeRepository
import de.kataia.app.ui.screens.HomeScreen
import de.kataia.app.ui.screens.GalleryScreen
import de.kataia.app.ui.screens.ProgressScreen
import de.kataia.app.ui.screens.BadgeScreen
import de.kataia.app.ui.screens.ChallengeCompletionScreen
import de.kataia.app.ui.screens.ChallengeDetailScreen
import de.kataia.app.ui.viewmodel.ChallengeCompletionViewModel



@Composable
fun SetupNavGraph(
    navController: NavHostController,
    repository: ChallengeRepository,
    userPrefsRepository: UserPreferencesRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            val homeViewModel: de.kataia.app.ui.viewmodel.HomeViewModel = viewModel (
                factory = object : ViewModelProvider.Factory{
                    override fun <T : ViewModel> create(modelClass: Class<T>): T{
                        return de.kataia.app.ui.viewmodel.HomeViewModel(userPrefsRepository) as T
                    }
                }
            )

            val xpLevel by homeViewModel.userXp.collectAsState()

            HomeScreen(
                xpCount = xpLevel,
                onChallengeClick = { challengeId ->
                    navController.navigate(Screen.ChallengeDetail.route + "/$challengeId")
                }
            )
        }

        composable(route = Screen.Gallery.route) {GalleryScreen()}
        composable(route = Screen.Progress.route) {ProgressScreen()}
        composable(route = Screen.Badges.route) {BadgeScreen()}

        // 1. Detail-Screen mit Challenge-ID
        composable(route = Screen.ChallengeDetail.route + "/{challengeId}") { backStackEntry ->
            val challengeId = backStackEntry.arguments?.getString("challengeId") ?: ""
            ChallengeDetailScreen(
                challengeId = challengeId,
                onNavigateToCompletion = { id ->
                    navController.navigate("completion/$id")
                }
            )
        }

        // 2. Abschluss-Screen (Completion)
        composable(route = "completion/{challengeId}") { backStackEntry ->
            val challengeId = backStackEntry.arguments?.getString("challengeId") ?: ""

            val viewModel: ChallengeCompletionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ChallengeCompletionViewModel(repository, userPrefsRepository) as T
                    }
                }
            )

            ChallengeCompletionScreen(
                challengeId = challengeId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            )
        }
    }
}
