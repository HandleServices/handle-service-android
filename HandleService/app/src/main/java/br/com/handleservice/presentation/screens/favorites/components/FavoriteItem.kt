package br.com.handleservice.presentation.screens.favorites.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                painter = painterResource(R.drawable.ic_favorite_filled),
                contentDescription = "Favorito",
                tint = colorResource(R.color.handle_blue),
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = favorite.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 16.sp,
                    color = colorResource(R.color.handle_titles)
                )
                Text(
                    text = favorite.category,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = colorResource(R.color.handle_gray)
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.profile_image_fallback),
            contentDescription = "Imagem do favorito",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
