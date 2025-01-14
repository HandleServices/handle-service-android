package br.com.handleservice.presentation.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.notification.components.NotificationItem

@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val notifications by viewModel.notifications.collectAsState()

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
                text = "Notificações",
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

        Divider(
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            items(notifications) { notification ->
                NotificationItem(notification)
                Divider(
                    color = MaterialTheme.colorScheme.outline,
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}
