package br.com.handleservice.presentation.screens.contracts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.ui.mock.getMockOrders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class GroupedOrders(
    val scheduled: Map<String, List<Order>> = emptyMap(),
    val finished: Map<String, List<Order>> = emptyMap()
)

@HiltViewModel
class ContractsViewModel @Inject constructor() : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    init {
        loadContracts()
    }

    private fun loadContracts() {
        viewModelScope.launch {
            _orders.value = getMockOrders()
        }
    }

    val groupedContracts: StateFlow<GroupedOrders> = _orders.map { contracts ->
        GroupedOrders(
            scheduled = contracts
                .filter { it.status === OrderStatus.IN_PROGRESS || it.status === OrderStatus.PENDING }
                .groupBy { formatDate(it.appointmentDate) },
            finished = contracts
                .filter { it.status === OrderStatus.FINISHED || it.status === OrderStatus.CANCELED }
                .groupBy { formatDate(it.appointmentDate) }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, GroupedOrders())

    private fun formatDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM")
        return date.format(formatter)
    }

}