package br.com.handleservice.presentation.navigation

sealed class Route(
    val route: String
) {
    data object HomeScreen : Route(route = "homeScreen")
    data object Profile : Route(route = "profileScreen")
    data object Contracts : Route(route = "contractsScreen")
}