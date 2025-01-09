package br.com.handleservice.domain.model

data class Address (
    val id: Int,
    val type: String,
    val street: String,
    val neighborhood: String,
    val number: Int,
    val city: String,
    val state: String,
    val note: String? = null,
    val isSelected: Boolean = false,
)