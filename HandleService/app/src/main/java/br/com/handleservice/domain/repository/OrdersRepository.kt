package br.com.handleservice.domain.repository

import br.com.handleservice.domain.model.Order

interface OrdersRepository {
    suspend fun getAllOrders(): List<Order>
    suspend fun getOrderById(id: String): Order
}