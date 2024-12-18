package br.com.handleservice.presentation.navigation

import BottomNavigationBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import br.com.handleservice.presentation.screens.contracts.ContractsScreen
import br.com.handleservice.presentation.screens.favorites.FavoritesScreen
import br.com.handleservice.presentation.screens.home.HomeScreen
import br.com.handleservice.presentation.screens.profile.ProfileScreen
import br.com.handleservice.presentation.screens.notification.NotificationScreen
import br.com.handleservice.presentation.screens.settings.SettingsScreen

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

            composable(
                route = Route.Contracts.route
            ) {
                ContractsScreen()
            }

            navigation(
                startDestination = "profile/main",
                route = Route.Profile.route
            ) {
                composable("profile/main") {
                    ProfileScreen(
                        onNotificationClick = {
                            navController.navigate("profile/notification")
                        },
                        onSettingsClick = {
                            navController.navigate("profile/settings")
                        },
                        onFavoritesClick = {
                            navController.navigate("profile/favorites")
                        }
                    )
                }

                composable("profile/notification") {
                    NotificationScreen(navController = navController)
                }

                composable("profile/settings") {
                    SettingsScreen(navController = navController)
                }

                composable(
                    route = Route.Favorites.route
                ) {
                    FavoritesScreen(navController = navController)
                }

            }
        }
    }
}
