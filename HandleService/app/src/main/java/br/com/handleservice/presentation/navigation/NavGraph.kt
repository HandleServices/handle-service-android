package br.com.handleservice.presentation.navigation

import BottomNavigationBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.handleservice.presentation.screens.contracts.ContractsScreen
import br.com.handleservice.presentation.screens.home.HomeScreen
import br.com.handleservice.presentation.screens.profile.ProfileScreen
import br.com.handleservice.presentation.screens.simple_search.ServiceListScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = Route.HomeScreen.route
            ) {
                HomeScreen()
            }

            composable (
                route =
                Route.Contracts.route
            ) {
                ContractsScreen()
            }

            composable (
                route =
                Route.SimpleSearch.route
            ) {
                ServiceListScreen()
            }

            composable (
                route =
                Route.Profile.route
            ) {
                ProfileScreen()
            }
        }
    }
}