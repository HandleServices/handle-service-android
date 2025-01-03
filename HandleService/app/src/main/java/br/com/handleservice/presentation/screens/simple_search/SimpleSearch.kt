package br.com.handleservice.presentation.screens.simple_search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import coil.compose.rememberImagePainter

@Composable
fun ServiceListScreen() {
    val serviceList = listOf(
        ServiceItem(
            name = "Encanamentos & Cia",
            rating = 4.8,
            category = "Encanador",
            isAvailableNow = true,
            imageUrl = "https://via.placeholder.com/150"
        ),
        ServiceItem(
            name = "Chaveiro Abre Tudo",
            rating = 4.8,
            category = "Encanador",
            isAvailableNow = false,
            imageUrl = "https://via.placeholder.com/150"
        ),
        ServiceItem(
            name = "Marcenaria Madero",
            rating = 4.8,
            category = "Encanador",
            isAvailableNow = false,
            imageUrl = "https://via.placeholder.com/150"
        ),
        ServiceItem(
            name = "Beleza Todo Dia - Esmaltaria",
            rating = 4.8,
            category = "Encanador",
            isAvailableNow = true,
            imageUrl = "https://via.placeholder.com/150"
        ),
        ServiceItem(
            name = "Edificações Ltda",
            rating = 4.8,
            category = "Encanador",
            isAvailableNow = false,
            imageUrl = "https://via.placeholder.com/150"
        ),
    )

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HandleSearchBar<ServiceItem>(
                placeholder = "Buscar em Manutenção & Reparos",
            )

            Text(
                text = "Sugestões",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = { /* Filtros action */ }) {
                    Text("Filtros")
                }
                OutlinedButton(onClick = { /* Ordenar por action */ }) {
                    Text("Ordenar por")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(serviceList) { item ->
                    ServiceItemCard(item)
                }
            }
        }
    }
}

data class ServiceItem(
    val name: String,
    val rating: Double,
    val category: String,
    val isAvailableNow: Boolean,
    val imageUrl: String
)

@Composable
fun ServiceItemCard(item: ServiceItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column () {
                Row {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp
                    )

                    if (item.isAvailableNow) {
                        Text(
                            text = "Disponível Agora!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 7.sp,
                            )
                        )
                    }
                }

                Row {
                    val ratingAndCategory = "${item.rating} • ${item.category}"
                    Text(
                        text = ratingAndCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = rememberImagePainter(data = item.imageUrl),
                contentDescription = item.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview()
@Composable
fun SimpleSearchPreview() {
    Scaffold {
        ServiceListScreen()
    }
}

