package br.com.handleservice.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.domain.model.Service
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import br.com.handleservice.presentation.screens.home.components.DoubleHomeScreenColumn
import br.com.handleservice.ui.components.handleHeader.HandleHeader


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
        .background(colorResource(R.color.background)),
    navController: androidx.navigation.NavController,
) {
    val handleButtonClick: (ServicesCategoriesType) -> Unit = { cat ->
        println("Navigating to: ${cat.url}")
    }

    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(vertical = 26.dp),
        horizontalAlignment = Alignment.Start
    ) {
        HandleHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
                .padding(bottom = 26.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Column {
                Text(
                    text = "Olá, Gabriela",
                    textAlign = TextAlign.Start,
                    fontSize = 21.sp,
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
                    value = query,
                    onValueChange = { newText -> query = newText },
                    onSearch = {
                        if (query.isNotEmpty()) {
                            // TO-DO: Mudar para pesquisa simplificada, usar a query tbm
                           // navController.navigate("worker_screen/$query")
                        }
                    },
                    modifier = Modifier.height(32.dp),
                    placeholder = "Buscar pela handle"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Categorias",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            DoubleHomeScreenColumn(categories = ServicesCategories.categories, onClick = handleButtonClick)
        }
    }
}