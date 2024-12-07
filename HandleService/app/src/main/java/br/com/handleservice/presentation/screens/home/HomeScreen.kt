package br.com.handleservice.presentation.screens.home

import br.com.handleservice.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.presentation.components.searchbar.HandleSearchBar
import br.com.handleservice.presentation.components.searchbar.SearchableClass

data class Service(
    val name: String,
    val iconResId: Int,
    val url: String
) : SearchableClass {
    override fun doesMatchSearchQuery(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

@Composable
fun HomeScreenButton(service: Service, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(167.dp)
            .height(64.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCEE1F2))
    ) {
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight()
                .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            Text(
                text = service.name,
                color = Color(0xFF1A73E8),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        ) {
            Icon(
                painter = painterResource(id = service.iconResId),
                contentDescription = service.name,
                tint = Color(0xFF1A73E8),
                modifier = Modifier
                    .size(44.dp)
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 6.dp),
            )
        }
    }
}

@Composable
fun DoubleHomeScreenColumn(serviceList: List<Service>, onClick: (Service) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            serviceList.forEach { service ->
                item {
                    HomeScreenButton(service = service, onClick = { onClick(service) })
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier.background(Color(0xFFF4F5F8))) {
    val handleButtonClick: (Service) -> Unit = { service ->
        println("Navigating to: ${service.url}")
    }

    // TODO: Alterar para o consumo de uma API
    val serviceList: List<Service> = listOf(
        Service("Eletricista", R.drawable.electrical_service, "elet"),
        Service("Professor", R.drawable.mortarboard, "elet"),
        Service("Desenvolvedor", R.drawable.developer, "elet"),
        Service("Personal Trainer", R.drawable.healthcheck, "elet"),
        Service("Veterinário", R.drawable.paw, "elet"),
        Service("Fotógrafo", R.drawable.color_patern, "elet"),
        Service("Diarista", R.drawable.day_laborer, "elet"),
        Service("Contador", R.drawable.safe_pig, "elet"),
        Service("Cabeleireiro", R.drawable.hairdresser, "elet"),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .weight(200f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(0.dp),
                contentAlignment = Alignment.Center
            ) {
                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedButton(
                    onClick = { println("Address button clicked!") },
                    modifier = Modifier
                        .width(200.dp)
                        .height(20.dp)
                        .padding(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8)),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "R. Franco Costa, 78",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 12.sp
                        )
                    )
                }
            }

            IconButton(
                onClick = { println("Location icon clicked!") },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Location icon",
                    tint = Color(0xFF1A73E8),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text(
                text = "Olá, Gabriela",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.Black
            )
            Text(
                text = "o que você procura hoje?",
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF7C7C8A)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            HandleSearchBar<Service>(
                searchableElements = serviceList,
                modifier = Modifier.height(32.dp),
                placeholder = {
                    Text("Buscar pela handle", color = Color.Gray)
                },
                cornerRadius = 5.dp,
                icon = Icons.Default.Search,
                iconColor = Color.Gray,
                elevation = 8.dp, // Ajuste se necessário
                shadowColor = Color.Black.copy(alpha = 0.5f) // Ajuste a cor e a alpha se necessário
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Categorias",
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(15.dp))

        DoubleHomeScreenColumn(serviceList = serviceList, onClick = handleButtonClick)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(backgroundColor = 0xFFF4F5F8)
@Composable
fun HomeScrenPreview() {
    Scaffold {
        HomeScreen()
    }
}
