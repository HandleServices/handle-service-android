package br.com.handleservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import br.com.handleservice.presentation.navigation.NavGraph
import br.com.handleservice.presentation.navigation.Route
import br.com.handleservice.presentation.screens.settings.SettingsViewModel
import br.com.handleservice.ui.theme.HandleServiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val isDarkTheme by viewModel.darkModeEnabled.collectAsState()

            HandleServiceTheme(darkTheme = isDarkTheme) {
                NavGraph(
                    startDestination = Route.HomeScreen.route,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { viewModel.toggleDarkMode(it) }
                )
            }
        }
    }
}
