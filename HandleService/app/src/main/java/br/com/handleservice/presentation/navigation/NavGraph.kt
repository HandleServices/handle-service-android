package br.com.handleservice.presentation.navigation

import BottomNavigationBar
import ContractsScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import br.com.handleservice.presentation.screens.address.AddressScreen
import br.com.handleservice.presentation.screens.chat.ChatDetailScreen
import br.com.handleservice.presentation.screens.chat.ChatScreen
import br.com.handleservice.presentation.screens.favorites.FavoritesScreen
import br.com.handleservice.presentation.screens.favorites.FavoritesViewModel
import br.com.handleservice.presentation.screens.home.HomeScreen
import br.com.handleservice.presentation.screens.profile.ProfileScreen
import br.com.handleservice.presentation.screens.notification.NotificationScreen
import br.com.handleservice.presentation.screens.notification.NotificationViewModel
import br.com.handleservice.presentation.screens.settings.SettingsScreen
import br.com.handleservice.presentation.screens.settings.SettingsViewModel
import br.com.handleservice.presentation.screens.simple_search.SearchScreen
import br.com.handleservice.presentation.screens.worker.WorkerScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    startDestination: String,
    favoritesViewModel: FavoritesViewModel,
    notificationViewModel: NotificationViewModel,
    settingsViewModel: SettingsViewModel,
    onToggleTheme: (Boolean) -> Unit,
    isDarkTheme: Boolean
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
                route = Route.HomeScreen.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                HomeScreen(navController = navController)
            }

            composable(
                route = Route.Contracts.route,
                enterTransition = { fadeIn(animationSpec = tween(500)) + slideInVertically { it } },
                exitTransition = { fadeOut(animationSpec = tween(500)) + slideOutVertically { it } },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) + slideInVertically { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(500)) + slideOutVertically { -it } }
            ) {
                ContractsScreen(navController = navController)
            }

            composable(
                route = "${Route.SearchScreen.route}/{query}",
                arguments = listOf(navArgument("query") { type = NavType.StringType; defaultValue = "" }),
                enterTransition = { fadeIn(animationSpec = tween(800)) + slideInVertically { it } },
                exitTransition = { fadeOut(animationSpec = tween(800)) + slideOutVertically { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(800)) + slideInVertically { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(800)) + slideOutVertically { it } }
            ) { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query").orEmpty()
                SearchScreen(query = query, navController = navController)
            }

            composable(
                route = "worker_screen/{worker-id}",
                arguments = listOf(navArgument("worker-id") { type = NavType.IntType }),
                enterTransition = { fadeIn(animationSpec = tween(500)) + expandIn() },
                exitTransition = { fadeOut(animationSpec = tween(500)) + shrinkOut() },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) + expandIn() },
                popExitTransition = { fadeOut(animationSpec = tween(500)) + shrinkOut() }
            ) { backStackEntry ->
                val workerId = backStackEntry.arguments?.getInt("worker-id") ?: 0
                WorkerScreen(
                    workerId = workerId,
                    navController = navController,
                    favoritesViewModel = favoritesViewModel,
                    notificationViewModel = notificationViewModel,
                    settingsViewModel = settingsViewModel
                )
            }

            composable(
                route = Route.Profile.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                ProfileScreen(
                    navController = navController,
                    onNotificationClick = {
                        navController.navigate(Route.Notification.route)
                    },
                    onSettingsClick = {
                        navController.navigate(Route.Settings.route)
                    },
                    onFavoritesClick = {
                        navController.navigate(Route.Favorites.route)
                    },
                    onAddressClick = {
                        navController.navigate(Route.Address.route)
                    },
                    onChatClick = {
                        navController.navigate(Route.Chat.route)
                    }
                )
            }

            // Aplicando as animações de Favorites para todas as telas filhas de Profile
            composable(
                Route.Notification.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                NotificationScreen(
                    navController = navController,
                    viewModel = notificationViewModel
                )
            }

            composable(
                Route.Settings.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                SettingsScreen(
                    navController = navController,
                    viewModel = settingsViewModel
                )
            }

            composable(
                route = Route.Favorites.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                FavoritesScreen(
                    navController = navController,
                    favoritesViewModel = favoritesViewModel,
                    settingsViewModel = settingsViewModel
                )
            }

            composable(
                route = Route.Address.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                AddressScreen(navController = navController)
            }

            composable(
                route = Route.Chat.route,
                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { it } },
                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { -it } },
                popEnterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally { -it } },
                popExitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally { it } }
            ) {
                ChatScreen(navController = navController)
            }

            composable(
                route = "chat_detail/{userName}",
                arguments = listOf(navArgument("userName") { type = NavType.StringType })
            ) { backStackEntry ->
                val userName = backStackEntry.arguments?.getString("userName") ?: "Desconhecido"
                ChatDetailScreen(navController = navController, userName = userName)
            }

        }
    }
}
