package br.com.handleservice.data.repository

import android.util.Log
import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.repository.OrdersRepository
import javax.inject.Inject
import javax.inject.Singleton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Singleton
class OrdersRepositoryImpl @Inject constructor(
    private val apiService: OrdersApiService
) : OrdersRepository {

    private fun parseAppointmentDate(dateStr: String): LocalDateTime {
        return try {
            if (dateStr.length == 16) {
                LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
            } else {
                LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME)
            }
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error parsing appointmentDate: ${e.localizedMessage}")
            throw e
        }
    }

    private fun parsePurchaseTime(timeStr: String): LocalDateTime {
        return try {
            LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX"))
        } catch (e: Exception) {
            LocalDateTime.parse(timeStr, DateTimeFormatter.ISO_DATE_TIME)
        }
    }

    override suspend fun getAllOrders(): List<Order> {
        try {
            return apiService.getAllOrders().map { apiOrder ->
                Order(
                    id = apiOrder.id,
                    appointmentDate = parseAppointmentDate(apiOrder.appointmentDate.toString()),
                    value = apiOrder.value,
                    purchaseTime = parsePurchaseTime(apiOrder.purchaseTime.toString()),
                    workerRating = apiOrder.workerRating,
                    workerId = apiOrder.workerId,
                    serviceId = apiOrder.serviceId,
                    status = OrderStatus.valueOf(apiOrder.status.toString())
                )
            }
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error fetching orders: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getOrderById(id: Int): Order {
        try {
            val apiOrder = apiService.getOrderById(id)
            return Order(
                id = apiOrder.id,
                appointmentDate = parseAppointmentDate(apiOrder.appointmentDate.toString()),
                value = apiOrder.value,
                purchaseTime = parsePurchaseTime(apiOrder.purchaseTime.toString()),
                workerRating = apiOrder.workerRating,
                workerId = apiOrder.workerId,
                serviceId = apiOrder.serviceId,
                status = OrderStatus.valueOf(apiOrder.status.toString())
            )
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error fetching order by id: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun updateOrder(id: Int, orderUpdate: OrderUpdateDTO): Order {
        return try {
            apiService.editOrder(id, orderUpdate)
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error editing order: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun deleteOrder(id: Int): String {
        return try {
            apiService.deleteOrder(id)
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error deleting order: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun createOrder(orderCreateDTO: OrderCreateDTO): Order {
        return try {
            apiService.createOrder(orderCreateDTO)
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error creating order: ${e.localizedMessage}")
            throw e
        }
    }
}
