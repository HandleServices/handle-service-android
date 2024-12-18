package br.com.handleservice.presentation.navigation

sealed class Route(
    val route: String
) {
    data object HomeScreen : Route(route = "homeScreen")
    data object Profile : Route(route = "profileScreen")
    data object Contracts : Route(route = "contractsScreen")
    data object Notification : Route(route = "notification_screen")
    data object Settings : Route(route = "settings_screen")
    data object Favorites : Route(route = "favorites_screen")
}