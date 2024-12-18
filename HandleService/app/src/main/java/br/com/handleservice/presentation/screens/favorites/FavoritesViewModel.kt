package br.com.handleservice.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class Favorite(
    val name: String,
    val category: String,
    val isAvailable: Boolean
)


@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val _favorites = MutableStateFlow(
        listOf(
            Favorite(name = "Encanamentos & Cia", category = "Encanador • Eletricista", isAvailable = true),
            Favorite(name = "Pedreiro Jorge", category = "Pedreiro • Construtor", isAvailable = false)
        )
    )

    val favorites: StateFlow<List<Favorite>> = _favorites
}
