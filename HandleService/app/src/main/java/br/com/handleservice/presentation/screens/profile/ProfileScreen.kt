package br.com.handleservice.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.profile.components.ProfileOption

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onFavoritesClick: () -> Unit,
) {
    val profileState = viewModel.profileState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.profile_image_fallback),
                contentDescription = "Foto de Perfil",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = profileState.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W600),
                fontSize = 20.sp,
                color = colorResource(R.color.handle_titles)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        ProfileOption(
            icon = R.drawable.ic_filled_address,
            label = "Meus Endereços",
            description = "Endereços cadastrados",
            onClick = { }
        )
        ProfileOption(
            icon = R.drawable.ic_favorite,
            label = "Favoritos",
            description = "Os seus preferidos estarão aqui!",
            onClick = onFavoritesClick
        )
        ProfileOption(
            icon = R.drawable.ic_notification,
            label = "Notificações",
            description = "Minhas informações",
            onClick = onNotificationClick
        )
        ProfileOption(
            icon = R.drawable.ic_settings,
            label = "Configurações",
            description = "Faça ajustes no seu app",
            onClick = onSettingsClick
        )
    }
}
