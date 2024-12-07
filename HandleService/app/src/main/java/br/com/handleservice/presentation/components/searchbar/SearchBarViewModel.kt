package br.com.handleservice.presentation.components.searchbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface SearchableClass {
    fun doesMatchSearchQuery(query: String): Boolean
}

@ViewModelScoped
class SearchBarViewModel<T : SearchableClass> @Inject constructor() : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchableElements = MutableStateFlow<List<T>>(emptyList())
    val searchableElements = _searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_searchableElements) { query, allElements ->
            if (query.isBlank()) {
                allElements
            } else {
                delay(2000L)  // simulate delay for search query
                allElements.filter { it.doesMatchSearchQuery(query) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
    }

    fun setSearchableElements(elements: List<T>) {
        _searchableElements.value = elements
    }
}
