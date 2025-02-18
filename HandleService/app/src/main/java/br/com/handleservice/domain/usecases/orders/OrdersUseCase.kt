package br.com.handleservice.domain.usecases.orders

import jakarta.inject.Inject

class OrdersUseCase @Inject constructor(
    val listOrders: GetAllOrdersUseCase,
    val getOrderByIdUseCase: GetOrderByIdUseCase,
    val createOrderUseCase: CreateOrderUseCase,
    val cancelOrderUseCase: CancelOrderUseCase
) {

}