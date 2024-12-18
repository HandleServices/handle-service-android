package br.com.handleservice.presentation.navigation

sealed class Route(
    val route: String
) {
    data object HomeScreen : Route(route = "homeScreen")
    data object Profile : Route(route = "profileScreen")
    data object Contracts : Route(route = "contractsScreen")
    data object Notification : Route(route = "profile/notification")
    data object Settings : Route(route = "profile/settings")
    data object Favorites : Route(route = "profile/favorites")
}