package br.com.handleservice.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class Favorite(
    val id: String,
    val name: String,
    val category: String,
    val isAvailable: Boolean
)

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val _favorites = MutableStateFlow( listOf<Favorite>() )
    val favorites: StateFlow<List<Favorite>> = _favorites

    // Adicionar um novo favorito
    fun addFavorite(favorite: Favorite) {
        println("Adicionando favorito: ${favorite.name}")
        _favorites.update { currentFavorites ->
            if (currentFavorites.none { it.name == favorite.name }) {
                val updatedList = currentFavorites + favorite
                println("Lista de favoritos após adicionar: ${updatedList.joinToString { it.name }}")
                updatedList
            } else {
                println("Favorito já existe! Lista atual: ${currentFavorites.joinToString { it.name }}")
                currentFavorites
            }
        }
        println("Depois da atualização de inserção: ${_favorites.value.joinToString { it.name }}")
    }

    // Remover um favorito existente
    fun removeFavorite(favorite: Favorite) {
        println("Removendo favorito: ${favorite.name}")
        _favorites.update { currentFavorites ->
            val updatedList = currentFavorites.filter { it.name != favorite.name }
            println("Lista de favoritos após remover: ${updatedList.joinToString { it.name }}")
            updatedList
        }
        println("Depois da atualização de tirar: ${_favorites.value.joinToString { it.name }}")
    }

    init {
        favorites.onEach {
            println("Estado de favoritos atualizado: ${it.joinToString { fav -> fav.name }}")
        }.launchIn(viewModelScope)
    }

}

