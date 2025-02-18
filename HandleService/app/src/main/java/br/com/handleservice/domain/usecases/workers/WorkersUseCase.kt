package br.com.handleservice.domain.usecases.services

import jakarta.inject.Inject

class WorkersUseCase @Inject constructor(
    val listOrders: GetAllOrdersUseCase,
    val getOrderByIdUseCase: GetOrderByIdUseCase,
    val createOrderUseCase: CreateOrderUseCase,
    val cancelOrderUseCase: CancelOrderUseCase
) {

}