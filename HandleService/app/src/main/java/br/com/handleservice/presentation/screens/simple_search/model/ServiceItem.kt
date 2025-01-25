package br.com.handleservice.presentation.screens.simple_search.model

data class ServiceItem(
    val id: Int,
    val name: String,
    val rating: Double,
    val category: String,
    val isAvailableNow: Boolean,
    val imageUrl: String
)
