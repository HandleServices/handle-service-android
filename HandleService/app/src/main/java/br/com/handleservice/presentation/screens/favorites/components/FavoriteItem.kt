package br.com.handleservice.presentation.screens.favorites.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.favorites.Favorite

@Composable
fun FavoriteItem(favorite: Favorite) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.80f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_favorite_filled),
                contentDescription = "Favorito",
                tint = colorResource(R.color.handle_blue),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = favorite.name,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        fontSize = 16.sp,
                        color = colorResource(R.color.handle_titles)
                    )
                    if (favorite.isAvailable) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Disponível Agora!",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 9.sp,
                            color = colorResource(R.color.handle_blue)
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = colorResource(R.color.handle_gray_secondary)
                )
                Text(
                    text = favorite.category,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = colorResource(R.color.handle_gray)
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.profile_image_fallback),
            contentDescription = "Imagem do favorito",
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
