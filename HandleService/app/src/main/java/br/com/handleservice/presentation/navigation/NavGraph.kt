package br.com.handleservice.presentation.navigation

import BottomNavigationBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.handleservice.presentation.screens.contracts.ContractsScreen
import br.com.handleservice.presentation.screens.favorites.FavoritesScreen
import br.com.handleservice.presentation.screens.favorites.FavoritesViewModel
import br.com.handleservice.presentation.screens.home.HomeScreen
import br.com.handleservice.presentation.screens.profile.ProfileScreen
import br.com.handleservice.presentation.screens.simple_search.ServiceListScreen
import br.com.handleservice.presentation.screens.notification.NotificationScreen
import br.com.handleservice.presentation.screens.notification.NotificationViewModel
import br.com.handleservice.presentation.screens.settings.SettingsScreen
import br.com.handleservice.presentation.screens.worker.WorkerScreen

@Composable
fun NavGraph(
    startDestination: String,
    favoritesViewModel: FavoritesViewModel,
    notificationViewModel: NotificationViewModel
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
                HomeScreen(
                    navController = navController
                )
            }

            composable(
                route = Route.Contracts.route
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
                        },
                        onAddresClick = {
                            navController.navigate("worker_screen/1")
                        }
                    )
                }

                composable(
                    Route.Notification.route
                ) {
                    NotificationScreen(
                        navController = navController,
                        viewModel = notificationViewModel
                    )
                }

                composable(
                    route = Route.Settings.route
                ) {
                    SettingsScreen(navController = navController)
                }

                composable(
                    route = Route.Favorites.route
                ) {
                    FavoritesScreen(
                        navController = navController,
                        favoritesViewModel = favoritesViewModel // Passa o ViewModel aqui
                    )
                }

                composable(
                    route = Route.WorkerScreen.route,
                    arguments = listOf(navArgument("work-id") { type = NavType.StringType })
                ) { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("work-id")
                    WorkerScreen(
                        query = query,
                        navController = navController,
                        favoritesViewModel = favoritesViewModel,
                        notificationViewModel = notificationViewModel
                    )
                }

            }
        }
    }
}
