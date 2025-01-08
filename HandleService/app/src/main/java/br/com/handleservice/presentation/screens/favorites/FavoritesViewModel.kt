package br.com.handleservice.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
            Favorite(
                name = "Pedreiro Jorge",
                category = "Pedreiro",
                isAvailable = false
            )
        )
    )
    val favorites: StateFlow<List<Favorite>> = _favorites

    // Adicionar um novo favorito
    fun addFavorite(favorite: Favorite) {
        _favorites.update { currentFavorites ->
            if (currentFavorites.none { it.name == favorite.name }) {
                currentFavorites + favorite
            } else currentFavorites
        }
    }

    // Remover um favorito existente
    fun removeFavorite(favorite: Favorite) {
        _favorites.update { currentFavorites ->
            currentFavorites.filter { it.name != favorite.name }
        }
    }
}
