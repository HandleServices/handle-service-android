package br.com.handleservice.presentation.screens.favorites.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun FavoriteItem(
    favorite: Favorite,
    onFavoriteClick: (Favorite) -> Unit,
    onWorkerClick: (Favorite) -> Unit,
    animationsEnabled: Boolean
) {
    var isRemoving by remember { mutableStateOf(false) }

    fun removeFavorite() {
        isRemoving = true
    }

    AnimatedVisibility(
        visible = !isRemoving, // Só será visível enquanto `isRemoving` for falso.
        enter = fadeIn(animationSpec = tween(durationMillis = 800)),
        exit = fadeOut(animationSpec = tween(durationMillis = 800)) + shrinkVertically(
            animationSpec = tween(durationMillis = 800)
        ) + scaleOut(
            targetScale = 0.6f,
            animationSpec = tween(durationMillis = 800)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                .clickable { onWorkerClick(favorite) }
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
                    contentDescription = "Remover favorito",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            removeFavorite() // Inicia a animação de remoção
                        }
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = favorite.name,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (favorite.isAvailable) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Disponível Agora!",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 9.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = favorite.category,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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

    // Remover favorito após a animação
    if (isRemoving) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(if (animationsEnabled) 300 else 0)
            onFavoriteClick(favorite) // Remove o item do ViewModel
        }
    }
}
