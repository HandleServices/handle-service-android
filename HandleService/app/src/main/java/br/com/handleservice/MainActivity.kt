package br.com.handleservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import br.com.handleservice.presentation.navigation.NavGraph
import br.com.handleservice.presentation.navigation.Route
import br.com.handleservice.ui.theme.HandleServiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HandleServiceTheme {
                Box(modifier = Modifier.background(colorResource(id = R.color.background))) {
                    NavGraph(startDestination = Route.HomeScreen.route)
                }
            }
        }
    }
}

