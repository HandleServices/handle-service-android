package br.com.handleservice.domain.usecases.orders

import br.com.handleservice.domain.repository.OrdersRepository
import jakarta.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(orderId: Int) {
        ordersRepository.deleteOrder(orderId)
    }
}
