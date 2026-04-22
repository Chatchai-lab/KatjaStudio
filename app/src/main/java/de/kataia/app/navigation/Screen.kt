package de.kataia.app.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Gallery : Screen("gallery")
    object Progress : Screen("progress")
    object Badges : Screen("badges")
    object ChallengeDetail : Screen("challenge_detail")
}