package br.com.handleservice.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.domain.model.Service
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import br.com.handleservice.presentation.screens.home.components.DoubleHomeScreenColumn
import br.com.handleservice.ui.components.handleHeader.HandleHeader


@Composable
fun HomeScreen(modifier: Modifier = Modifier.background(Color(0xFFF4F5F8))) {
    val handleButtonClick: (JobsCategoriesType) -> Unit = { cat ->
        println("Navigating to: ${cat.url}")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(26.dp),
    ) {
        HandleHeader()
        Spacer(modifier = Modifier.height(15.dp))

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
                // searchableElements = JobsCategories.categories,
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

        DoubleHomeScreenColumn(categories = JobsCategories.categories, onClick = handleButtonClick)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview()
@Composable
fun HomeScreenPreview() {
    Scaffold {
        HomeScreen()
    }
}
