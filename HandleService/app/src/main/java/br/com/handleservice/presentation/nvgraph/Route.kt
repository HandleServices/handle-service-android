package br.com.handleservice.presentation.nvgraph

sealed class Route(
    val route: String
) {
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object HomeScreen : Route(route = "homeScreen")
    data object Screen : Route(route = "homeScreen")

}