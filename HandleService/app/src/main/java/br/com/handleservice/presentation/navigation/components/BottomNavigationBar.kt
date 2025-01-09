import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.handleservice.R
import br.com.handleservice.presentation.navigation.Route
import br.com.handleservice.presentation.navigation.components.getNavItems

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navItems = getNavItems()

    val selectedColor = colorResource(id = R.color.handle_blue)
    val unselectedColor = colorResource(id = R.color.handle_gray)

    Box(
        modifier = Modifier.zIndex(1f)
    ) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.shadow(15.dp)
        ) {
            navItems.forEach { item ->
                val isSelected = isItemSelected(item.route, currentDestination)

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = if (isSelected) item.filledIcon else item.outlinedIcon,
                            contentDescription = null,
                            tint = if (isSelected) selectedColor else unselectedColor
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (isSelected) selectedColor else unselectedColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

fun isItemSelected(route: String, currentDestination: NavDestination?): Boolean {
    val currentRoute = currentDestination?.route

    return when {
        currentRoute?.startsWith("worker_screen/") == true && route == Route.HomeScreen.route -> true
        currentRoute?.startsWith("profile/") == true && route == Route.Profile.route -> true
        currentRoute == route -> true
        else -> false
    }
}
