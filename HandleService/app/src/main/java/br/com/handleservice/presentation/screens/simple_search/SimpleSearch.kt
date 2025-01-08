package br.com.handleservice.presentation.screens.simple_search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.simple_search.components.FilterButton
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import br.com.handleservice.ui.mock.getMockWorker
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class ServiceItem(
    val id: Int,
    val name: String,
    val rating: Double,
    val category: String,
    val isAvailableNow: Boolean,
    val imageUrl: String
)

@Composable
fun SearchScreen(query: String?, navController: NavController?) {
    val serviceList = remember {
        getMockWorker().map { worker ->
            ServiceItem(
                id = worker.id,
                name = worker.businessName,
                rating = 4.5, // Valor fixo para rating (mock)
                category = worker.job,
                isAvailableNow = worker.isAvailable,
                imageUrl = worker.profilePicUrl
            )
        }.toList()
    }

    val filteredList = if (!query.isNullOrBlank()) {
        val normalizedQuery = query.replace("_", " ").lowercase()
        serviceList.filter {
            it.category.lowercase().contains(normalizedQuery) || it.name.lowercase().contains(normalizedQuery)
        }
    } else {
        serviceList
    }

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HandleHeader(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                hasBack = true
            )

            HandleSearchBar<ServiceItem>(
                placeholder = "Buscar em Manutenção & Reparos",
                value = query ?: "",
                onValueChange = { /* Handle inline search */ },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
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
                items(filteredList) { item ->
                    ServiceItemCard(item = item, navController = navController)
                }
            }
        }
    }
}

@Composable
fun ServiceItemCard(item: ServiceItem, navController: NavController?) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                println(item.id)
                navController?.navigate("worker_screen/${item.id}")
            }
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
                        lineHeight = TextUnit(20F, TextUnitType.Sp)
                    )

                    if (item.isAvailableNow) {
                        Text(
                            text = "Disponível Agora!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(R.color.handle_blue),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Divider(
                    color = colorResource(R.color.handle_gray_secondary),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Rating",
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

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.profile_image_fallback)
                    .build(),
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
fun SearchScreenPreview() {
    SearchScreen(query = "Encanador", navController = null)
}
