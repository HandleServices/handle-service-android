package br.com.handleservice

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import br.com.handleservice.presentation.navigation.NavGraph
import br.com.handleservice.presentation.navigation.Route
import br.com.handleservice.presentation.screens.favorites.FavoritesViewModel
import br.com.handleservice.presentation.screens.notification.NotificationViewModel
import br.com.handleservice.presentation.screens.settings.SettingsViewModel
import br.com.handleservice.ui.theme.HandleServiceTheme
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val favoritesViewModel: FavoritesViewModel by viewModels() // Instância compartilhada
    private val notificationViewModel: NotificationViewModel by viewModels()

    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationUtils.createNotificationChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }

        setContent {
            val viewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val isDarkTheme by viewModel.darkModeEnabled.collectAsState()

            HandleServiceTheme {
                Box(modifier = Modifier.background(colorResource(id = R.color.background))) {
                    NavGraph(
                        startDestination = Route.HomeScreen.route,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { viewModel.toggleDarkMode(it) },
                        favoritesViewModel = favoritesViewModel,
                        notificationViewModel = notificationViewModel
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de notificação concedida!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissão de notificação negada.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
