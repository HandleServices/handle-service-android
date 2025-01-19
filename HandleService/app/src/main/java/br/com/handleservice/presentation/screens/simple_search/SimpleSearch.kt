package br.com.handleservice.presentation.screens.simple_search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.handleservice.presentation.screens.simple_search.components.ServiceItemCard
import br.com.handleservice.presentation.screens.simple_search.model.ServiceItem
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import br.com.handleservice.ui.mock.getMockWorker
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SearchScreen(query: String, navController: NavController?) {
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

    var filterValue by remember { mutableStateOf(query) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
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
                value = filterValue,
                onValueChange = { newText -> filterValue = newText },
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
                    color = MaterialTheme.colorScheme.onSurface
                )
                FilterButton(
                    text = "Filtros",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.tune),
                            contentDescription = "Filtro",
                            tint = MaterialTheme.colorScheme.primary,
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
                            tint = MaterialTheme.colorScheme.primary,
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(query = "Encanador", navController = null)
}
