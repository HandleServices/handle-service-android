package br.com.handleservice.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.settings.components.SettingsItem

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val darkModeEnabled by viewModel.darkModeEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(101.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Voltar",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() },
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 24.dp),
                textAlign = TextAlign.Center
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SettingsItem(
                title = "Notificações",
                description = "Ao desativar as notificações, você estará abrindo mão da exibição visual e sonora delas.",
                action = {
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(R.color.white),
                            uncheckedThumbColor = colorResource(R.color.white),
                            checkedTrackColor = colorResource(R.color.handle_blue),
                            uncheckedTrackColor = colorResource(R.color.handle_red),
                            uncheckedBorderColor = colorResource(R.color.transparent),
                            checkedBorderColor = colorResource(R.color.transparent)
                        )
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                thickness = 1.dp,
                color = colorResource(R.color.handle_gray_secondary)
            )

            SettingsItem(
                title = "Convidar amigos",
                description = "Curtiu nossa plataforma? Convide seus amigos!",
                action = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_open_in_new),
                        contentDescription = "Convidar amigos",
                        modifier = Modifier.size(32.dp),
                        tint = colorResource(R.color.handle_light_gray)
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                thickness = 1.dp,
                color = colorResource(R.color.handle_gray_secondary)
            )

            SettingsItem(
                title = "Modo escuro",
                description = "",
                action = {
                    Switch(
                        checked = darkModeEnabled,
                        onCheckedChange = { viewModel.toggleDarkMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(R.color.white),
                            uncheckedThumbColor = colorResource(R.color.white),
                            checkedTrackColor = colorResource(R.color.handle_blue),
                            uncheckedTrackColor = colorResource(R.color.handle_red),
                            uncheckedBorderColor = colorResource(R.color.transparent),
                            checkedBorderColor = colorResource(R.color.transparent)
                        )
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                thickness = 1.dp,
                color = colorResource(R.color.handle_gray_secondary)
            )
        }
    }
}