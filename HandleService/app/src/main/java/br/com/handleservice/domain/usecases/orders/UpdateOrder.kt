package br.com.handleservice.domain.usecases.orders

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.ui.components.loading.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(id: Int, order: OrderUpdateDTO): Flow<UiState<Order>> = flow {
        emit(UiState.Loading())
        try {
            val updatedOrder = ordersRepository.updateOrder(id, order)
            emit(UiState.Success(data = updatedOrder))
        } catch (e: IOException) {
            emit(UiState.Error(message = "Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(UiState.Error(message = "Unexpected error: ${e.localizedMessage}"))
        }
    }
}
