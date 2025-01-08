package br.com.handleservice.presentation.screens.simple_search

import android.annotation.SuppressLint
import br.com.handleservice.R
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.presentation.screens.simple_search.components.FilterButton
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
            imageUrl = "https://www.safetyandhealthmagazine.com/ext/resources/images/news/construction/older-male-construction-worker.jpg?t=1698244045&width=768"
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
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )


            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sugestões",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
                FilterButton(
                    text = "Filtros",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.tune),
                            contentDescription = "Filtro",
                            tint = Color.Black,
                            modifier = Modifier.size(10.dp)
                        )
                    },
                    onClick = { /* Filtros action */ }
                )
                FilterButton(
                    text = "Ordenar",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.arrow_down),
                            contentDescription = "Ordenar",
                            tint = Color.Black,
                            modifier = Modifier.size(10.dp)
                        )
                    },
                    onClick = { /* Ordenar por action */ }
                )
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
        colors = CardColors(Color.White, Color.White, Color.White, Color.White),
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
            Column(
                modifier = Modifier.fillMaxWidth(0.80f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.Black,
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W500),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        lineHeight = TextUnit(20F, TextUnitType(20)),
                        modifier = Modifier.fillMaxHeight()
                    )

                    if (item.isAvailableNow) {
                        Text(
                            text = "Disponível Agora!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(R.color.handle_blue),
                                fontWeight = FontWeight.Bold,
                                fontSize = 7.sp,
                                lineHeight = TextUnit(20f, TextUnitType.Sp)
                            )
                        )
                    }
                }
                HorizontalDivider(
                    color = colorResource(R.color.handle_gray_200),
                    thickness = 0.5.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Filtro",
                        tint = colorResource(R.color.handle_blue),
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "${item.rating}",
                        color = colorResource(R.color.handle_blue)
                    )
                    Text(
                        text = " • ${item.category}",
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
@Preview
@Composable
fun SimpleSearchPreview() {
    Scaffold {
        ServiceListScreen()
    }
}

