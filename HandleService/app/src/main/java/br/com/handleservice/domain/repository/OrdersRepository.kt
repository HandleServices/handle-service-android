package br.com.handleservice.domain.repository

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.domain.model.Order

interface OrdersRepository {
    suspend fun getAllOrders(): List<Order>
    suspend fun getOrderById(id: Int): Order
    suspend fun updateOrder(id: Int, orderUpdate: OrderUpdateDTO): Order
    suspend fun deleteOrder(id: Int): String
    suspend fun createOrder(orderCreateDTO: OrderCreateDTO): Order
}