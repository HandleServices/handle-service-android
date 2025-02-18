package br.com.handleservice.domain.usecases.orders

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.ui.components.loading.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(order: OrderCreateDTO): Flow<UiState<Order>> = flow {
        emit(UiState.Loading())
        try {
            val createdOrder = ordersRepository.createOrder(order)
            emit(UiState.Success(data = createdOrder))
        } catch (e: IOException) {
            emit(UiState.Error(message = "Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(UiState.Error(message = "Unexpected error: ${e.localizedMessage}"))
        }
    }
}
