package br.com.handleservice.presentation.screens.contracts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.usecases.orders.GetAllOrdersUseCase
import br.com.handleservice.ui.components.loading.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.contracts.contract

data class GroupedOrders(
    val scheduled: Map<String, List<Order>> = emptyMap(),
    val finished: Map<String, List<Order>> = emptyMap()
)

@HiltViewModel
class ContractsViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModel() {

    private val _orders = MutableStateFlow<UiState<List<Order>>>(UiState.Loading())
    val orders: StateFlow<UiState<List<Order>>> = _orders

    // Estado de carregamento
    val isLoading: StateFlow<Boolean> = _orders.map { uiState ->
        uiState is UiState.Loading
    }.stateIn(viewModelScope, SharingStarted.Lazily, true)

    val groupedContracts: StateFlow<GroupedOrders> = _orders.map { uiState ->
        when (uiState) {
            is UiState.Success -> {
                val contracts = uiState.data ?: emptyList()
                GroupedOrders(
                    scheduled = contracts
                        .filter { it.status == OrderStatus.IN_PROGRESS || it.status == OrderStatus.PENDING }
                        .groupBy { formatDate(it.appointmentDate) },
                    finished = contracts
                        .filter { it.status == OrderStatus.FINISHED || it.status == OrderStatus.CANCELED }
                        .groupBy { formatDate(it.appointmentDate) }
                )
            }
            else -> GroupedOrders()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, GroupedOrders())

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            _orders.value = UiState.Loading()
            //kotlinx.coroutines.delay(5000)

            getAllOrdersUseCase()
                .collect { uiState ->
                    _orders.value = uiState
                }
        }
    }

    private fun formatDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM")
        return date.format(formatter)
    }
}
