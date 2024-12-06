package br.com.handleservice.presentation.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import br.com.handleservice.R
import br.com.handleservice.presentation.navigation.Route

data class NavItem(
    val label: String,
    val filledIcon: Painter,
    val outlinedIcon: Painter,
    val route: String
)

@Composable
fun getNavItems(): List<NavItem> {

    val outlinedHomeIcon = painterResource(id = R.drawable.outlined_home_icon)
    val outlinedContractsIcon = painterResource(id = R.drawable.outlined_handshake_icon)
    val outlinedProfileIcon = painterResource(id = R.drawable.outlined_person_icon)
    val filledHomeIcon = painterResource(id = R.drawable.filled_home_icon)
    val filledContractsIcon = painterResource(id = R.drawable.filled_handshake_icon)
    val filledProfileIcon = painterResource(id = R.drawable.filled_person_icon)

    return remember {
        listOf(
            NavItem(
                label = "Home",
                outlinedIcon = outlinedHomeIcon,
                filledIcon = filledHomeIcon,
                route = Route.HomeScreen.route
            ),
            NavItem(
                label = "Contracts",
                outlinedIcon = outlinedContractsIcon,
                filledIcon = filledContractsIcon,
                route = Route.Contracts.route
            ),
            NavItem(
                label = "Profile",
                outlinedIcon = outlinedProfileIcon,
                filledIcon = filledProfileIcon,
                route = Route.Profile.route
            )
        )
    }
}
