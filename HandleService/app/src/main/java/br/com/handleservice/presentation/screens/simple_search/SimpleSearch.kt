package br.com.handleservice.presentation.screens.simple_search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.simple_search.components.FilterBottomSheet
import br.com.handleservice.presentation.screens.simple_search.components.FilterButton
import br.com.handleservice.presentation.screens.simple_search.components.ServiceItemCard
import br.com.handleservice.presentation.screens.simple_search.components.SortBottomSheet
import br.com.handleservice.presentation.screens.simple_search.model.ServiceItem
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.handleservice.presentation.screens.contracts.components.SkeletonCard
import kotlin.random.Random

@Composable
fun SearchScreen(query: String, navController: NavController?) {
    val viewModel: SimpleSearchViewModel = hiltViewModel()
    val workers by viewModel.workers.collectAsState()

    var serviceList by remember {
        mutableStateOf(
            workers.map { worker ->
                ServiceItem(
                    id = worker.id,
                    name = worker.businessName,
                    rating = (Math.round(Random.nextDouble(3.0, 5.0) * 10) / 10.0), // Mock do rating
                    category = worker.job,
                    isAvailableNow = worker.isAvailable,
                    imageUrl = worker.profilePicUrl
                )
            }
        )
    }

    var filteredList by remember { mutableStateOf(serviceList) }
    var filterValue by remember { mutableStateOf(query) }
    var selectedRating by remember { mutableStateOf<Int?>(null) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showSortSheet by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("default") }

    // ✅ Defina a função ANTES de chamá-la
    fun aplicarFiltrosEOrdenacao() {
        val normalizedQuery = filterValue.replace("_", " ").lowercase()

        filteredList = serviceList.filter { service ->
            // Mantém apenas os itens que correspondem à busca inicial
            service.category.lowercase().contains(normalizedQuery) || service.name.lowercase().contains(normalizedQuery)
        }.filter { service ->
            // Aplica o filtro de rating
            selectedRating == null || service.rating >= selectedRating!!
        }
    }

    // Atualiza os dados quando os workers forem carregados do backend
    LaunchedEffect(workers) {
        serviceList = workers.map { worker ->
            ServiceItem(
                id = worker.id,
                name = worker.businessName,
                rating = (Math.round(Random.nextDouble(3.0, 5.0) * 10) / 10.0), // Mock do rating
                category = worker.job,
                isAvailableNow = worker.isAvailable,
                imageUrl = worker.profilePicUrl
            )
        }
        aplicarFiltrosEOrdenacao() // ✅ Agora a função existe antes de ser chamada
    }

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
                onValueChange = { newText ->
                    filterValue = newText
                    aplicarFiltrosEOrdenacao()
                },
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
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.size(10.dp)
                        )
                    },
                    onClick = { showFilterSheet = true }
                )
                FilterButton(
                    text = "Ordenar",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.arrow_down),
                            contentDescription = "Ordenar",
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.size(10.dp)
                        )
                    },
                    onClick = { showSortSheet = true }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (filteredList.isEmpty()) {
                    items(5) {
                        SkeletonCard()
                    }
                } else {
                    items(filteredList) { item ->
                        ServiceItemCard(item = item, navController = navController)
                    }
                }
            }
        }
    }

    // BottomSheet de Filtros
    if (showFilterSheet) {
        FilterBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            onApplyFilters = { _, _, selectedRatingValue ->
                showFilterSheet = false
                selectedRating = selectedRatingValue
                aplicarFiltrosEOrdenacao()
            },
            onClearFilters = {
                showFilterSheet = false
                selectedRating = null
                filterValue = query
                aplicarFiltrosEOrdenacao()
            }
        )
    }

    // BottomSheet de Ordenação
    if (showSortSheet) {
        SortBottomSheet(
            onDismissRequest = { showSortSheet = false },
            onApplySort = { sortType ->
                showSortSheet = false
                selectedSort = sortType

                when (sortType) {
                    "rating" -> filteredList = filteredList.sortedByDescending { it.rating }
                    "default" -> aplicarFiltrosEOrdenacao()
                }
            },
            selectedSort = selectedSort
        )
    }
}
