package br.com.handleservice.presentation.screens.simple_search

import android.annotation.SuppressLint
import br.com.handleservice.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import br.com.handleservice.presentation.screens.simple_search.components.FilterButton
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import coil.compose.rememberImagePainter

data class ServiceItem(
    val name: String,
    val rating: Double,
    val category: String,
    val isAvailableNow: Boolean,
    val imageUrl: String
)

@Composable
fun SearchScreen(query: String?, navController: NavController?) {
    val serviceList = listOf(
        ServiceItem(
            name = "Eletricidade Rápida",
            rating = 4.8,
            category = "Eletricista",
            isAvailableNow = true,
            imageUrl = "https://inpolpolimeros.com.br/wp-content/uploads/2023/04/contratar-eletricista-scaled.jpg"
        ),
        ServiceItem(
            name = "Aulas de Matemática",
            rating = 4.6,
            category = "Professor",
            isAvailableNow = false,
            imageUrl = "https://cdn.pixabay.com/photo/2017/08/06/11/06/teacher-2590682_1280.jpg"
        ),
        ServiceItem(
            name = "Dev Rápido",
            rating = 4.7,
            category = "Desenvolvedor",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2015/09/05/22/46/programming-924920_1280.jpg"
        ),
        ServiceItem(
            name = "Treinamento Total",
            rating = 4.5,
            category = "Personal Trainer",
            isAvailableNow = false,
            imageUrl = "https://cdn.pixabay.com/photo/2016/03/27/21/34/sports-1280821_1280.jpg"
        ),
        ServiceItem(
            name = "Clínica Veterinária Pata Feliz",
            rating = 4.9,
            category = "Veterinário",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2015/08/31/10/52/veterinarian-914056_1280.jpg"
        ),
        ServiceItem(
            name = "Fotos Profissionais",
            rating = 4.8,
            category = "Fotógrafo",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/29/05/18/photographer-1867719_1280.jpg"
        ),
        ServiceItem(
            name = "Limpeza Completa",
            rating = 4.7,
            category = "Diarista",
            isAvailableNow = false,
            imageUrl = "https://cdn.pixabay.com/photo/2017/05/02/13/13/cleaning-2271201_1280.jpg"
        ),
        ServiceItem(
            name = "Contabilidade Rápida",
            rating = 4.6,
            category = "Contador",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2015/01/21/14/14/computer-606005_1280.jpg"
        ),
        ServiceItem(
            name = "Salão Beleza Total",
            rating = 4.8,
            category = "Cabeleireiro",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/19/14/00/hairdresser-1840523_1280.jpg"
        ),
        ServiceItem(
            name = "Eletricidade Express",
            rating = 4.7,
            category = "Eletricista",
            isAvailableNow = false,
            imageUrl = "https://cdn.pixabay.com/photo/2014/12/21/23/28/electrician-576688_1280.jpg"
        ),
        ServiceItem(
            name = "Aulas de Inglês",
            rating = 4.8,
            category = "Professor",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2015/01/26/22/40/teacher-613389_1280.jpg"
        ),
        ServiceItem(
            name = "Programador Especialista",
            rating = 4.9,
            category = "Desenvolvedor",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2015/02/02/11/09/office-621206_1280.jpg"
        ),
        ServiceItem(
            name = "Fitness Pessoal",
            rating = 4.5,
            category = "Personal Trainer",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2015/02/05/23/24/men-625251_1280.jpg"
        ),
        ServiceItem(
            name = "Cuidados Veterinários",
            rating = 4.6,
            category = "Veterinário",
            isAvailableNow = false,
            imageUrl = "https://cdn.pixabay.com/photo/2016/02/19/10/14/vet-1209820_1280.jpg"
        ),
        ServiceItem(
            name = "Estúdio Fotográfico",
            rating = 4.8,
            category = "Fotógrafo",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2016/03/09/09/22/woman-1245692_1280.jpg"
        ),
        ServiceItem(
            name = "Limpeza Perfeita",
            rating = 4.7,
            category = "Diarista",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2018/02/12/22/14/cleanliness-3156211_1280.jpg"
        ),
        ServiceItem(
            name = "Cabelos Incríveis",
            rating = 4.9,
            category = "Cabeleireiro",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2017/01/30/10/46/hair-2029929_1280.jpg"
        ),
        ServiceItem(
            name = "Contabilidade Inteligente",
            rating = 4.7,
            category = "Contador",
            isAvailableNow = false,
            imageUrl = "https://cdn.pixabay.com/photo/2018/05/02/09/45/financial-3360849_1280.jpg"
        ),
        ServiceItem(
            name = "Eletro Fácil",
            rating = 4.5,
            category = "Eletricista",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2014/11/23/10/38/technology-543520_1280.jpg"
        ),
        ServiceItem(
            name = "Fotógrafo de Eventos",
            rating = 4.9,
            category = "Fotógrafo",
            isAvailableNow = true,
            imageUrl = "https://cdn.pixabay.com/photo/2017/02/16/23/10/camera-2072178_1280.jpg"
        )
    )

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
                // Navega para o perfil do trabalhador
                navController?.navigate("worker_screen/${item.name}")
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
                    thickness = 1.dp, // Aumente a espessura para garantir visibilidade
                    modifier = Modifier.fillMaxWidth() // Certifique-se de que a linha preencha a largura do cartão
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
fun SearchScreenPreview() {
    SearchScreen(query = "Encanador", navController = null)
}