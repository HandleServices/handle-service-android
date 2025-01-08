package br.com.handleservice.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.handleservice.presentation.screens.home.ServicesCategoriesType

@Composable
fun DoubleHomeScreenColumn(categories: List<ServicesCategoriesType>, onClick: (ServicesCategoriesType) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            categories.forEach { category ->
                item {
                    HomeScreenButton(category = category, onClick = { onClick(category) })
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}