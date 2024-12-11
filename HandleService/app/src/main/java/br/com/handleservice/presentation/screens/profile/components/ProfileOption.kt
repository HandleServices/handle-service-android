package br.com.handleservice.presentation.screens.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R

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
                    modifier = Modifier.size(25.dp),
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
                    Spacer(modifier = Modifier.height(4.dp))
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
                    modifier = Modifier.size(25.dp),
                    tint = colorResource(R.color.handle_gray)
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = colorResource(R.color.handle_divider)
        )
    }
}
