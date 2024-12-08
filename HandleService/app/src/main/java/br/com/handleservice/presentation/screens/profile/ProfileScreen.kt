package br.com.handleservice.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import br.com.handleservice.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
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
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = colorResource(R.color.handle_titles)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        ProfileOption(
            icon = R.drawable.ic_messages,
            label = "Chat",
            description = "Meu histórico de conversas",
            onClick = { }
        )
        ProfileOption(
            icon = R.drawable.ic_address,
            label = "Meus Endereços",
            description = "Endereços cadastrados",
            onClick = { }
        )
        ProfileOption(
            icon = R.drawable.ic_document_data,
            label = "Dados da Conta",
            description = "Minhas informações",
            onClick = { }
        )
        ProfileOption(
            icon = R.drawable.ic_settings,
            label = "Configurações",
            description = "Faça ajustes no seu app",
            onClick = { }
        )
    }
}

@Composable
fun ProfileOption(
    icon: Int,
    label: String,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier.size(36.dp),
                    tint = colorResource(R.color.handle_light_gray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                        fontSize = 18.sp,
                        color = colorResource(R.color.handle_titles)
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = colorResource(R.color.handle_gray)
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "Seta",
                    modifier = Modifier.size(20.dp),
                    tint = colorResource(R.color.handle_gray)
                )
            }
        }
        Divider(
            color = colorResource(R.color.handle_divider),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}