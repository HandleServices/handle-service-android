package br.com.handleservice.presentation.screens.contracts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.usecases.orders.OrdersUseCase
import br.com.handleservice.ui.components.loading.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

import java.time.LocalDate

data class GroupedOrders(
    val scheduled: Map<LocalDate, List<Order>> = emptyMap(),
    val finished: Map<LocalDate, List<Order>> = emptyMap()
)


@HiltViewModel
class ContractsViewModel @Inject constructor(
    private val ordersUseCase: OrdersUseCase
) : ViewModel() {

    private val _orders = MutableStateFlow<UiState<List<Order>>>(UiState.Loading())
    val orders: StateFlow<UiState<List<Order>>> = _orders

    val isLoading: StateFlow<Boolean> = _orders.map { it is UiState.Loading }
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val groupedContracts: StateFlow<GroupedOrders> = _orders.map { uiState ->
        when (uiState) {
            is UiState.Success -> {
                val contracts = uiState.data ?: emptyList()
                GroupedOrders(
                    scheduled = contracts
                        .filter { it.status == OrderStatus.IN_PROGRESS || it.status == OrderStatus.PENDING }
                        .groupBy { it.appointmentDate.toLocalDate() }
                        .toSortedMap(),
                    finished = contracts
                        .filter { it.status == OrderStatus.FINISHED || it.status == OrderStatus.CANCELLED }
                        .groupBy { it.appointmentDate.toLocalDate() }
                        .toSortedMap()
                )
            }
            else -> GroupedOrders()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, GroupedOrders())

//    init {
//        fetchOrders()
//    }

    fun refreshOrders() {
        viewModelScope.launch {
            _orders.value = UiState.Loading()
            ordersUseCase.listOrders().collect { uiState ->
                _orders.value = uiState
            }
        }
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            _orders.value = UiState.Loading()
            kotlinx.coroutines.delay(5000)
            ordersUseCase.listOrders()
                .collect { uiState ->
                    _orders.value = uiState
                }
        }
    }

    fun cancelOrder(orderId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                ordersUseCase.cancelOrderUseCase(orderId)
                onSuccess()
            } catch (e: Exception) {
                Log.e("ContractsViewModel", "Error canceling order: ${e.localizedMessage}")
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun createOrder(order: Order, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                ordersUseCase.createOrderUseCase(
                    OrderCreateDTO(
                        appointmentDate = order.appointmentDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        value = order.value,
                        serviceId = order.serviceId,
                        workerId = order.workerId,
                        clientId = 1
                    )
                )
                onSuccess()
            } catch (e: Exception) {
                Log.e("ContractsViewModel", "Error creating order: ${e.localizedMessage}")
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun formatDate(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'às' HH:mm", Locale("pt", "BR"))
        return dateTime.format(formatter)
    }
}