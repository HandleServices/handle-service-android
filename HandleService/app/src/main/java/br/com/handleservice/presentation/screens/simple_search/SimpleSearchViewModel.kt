package br.com.handleservice.presentation.screens.simple_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handleservice.data.network.WorkersApiService
import br.com.handleservice.domain.model.Worker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimpleSearchViewModel @Inject constructor(
    private val workersApiService: WorkersApiService
) : ViewModel() {

    private val _workers = MutableStateFlow<List<Worker>>(emptyList())
    val workers: StateFlow<List<Worker>> = _workers

    init {
        viewModelScope.launch {
            try {
                val result = workersApiService.getAllWorkers()
                _workers.value = result
            } catch (e: Exception) {
                Log.e("SimpleSearchViewModel", e.toString())
            }
        }
    }
}
