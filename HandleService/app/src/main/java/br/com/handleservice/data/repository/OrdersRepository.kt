package br.com.handleservice.data.repository

import android.util.Log
import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.domain.repository.OrdersRepository
import jakarta.inject.Inject
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class OrdersRepository @Inject constructor(private val apiService: OrdersApiService) : OrdersRepository {

    override suspend fun getAllOrders(): List<Order> {
        try {
            val response = apiService.getAllOrders().map { apiOrder ->
                Order(
                    id = apiOrder.id,
                    appointmentDate = LocalDateTime.ofInstant(
                        Instant.parse(apiOrder.appointmentDate), ZoneId.systemDefault()), // Correct parsing
                    value = apiOrder.value, // Keep as Double
                    purchaseTime = LocalDateTime.ofInstant(
                        Instant.parse(apiOrder.purchaseTime), ZoneId.systemDefault()), // Correct parsing
                    workerRating = apiOrder.workerRating,
                    worker = Worker(
                        id = apiOrder.workerId,
                        firstName = "Placeholder",
                        businessName = "Placeholder",
                        lastName = "Placeholder",
                        job = "Placeholder",
                        email = "placeholder@example.com",
                        phone = "1234567890",
                        gender = "Male",
                        profilePicUrl = "https://example.com/profile.jpg",
                        isAvailable = true
                    ),
                    service = Service(
                        id = apiOrder.serviceId,
                        value = 50.0,
                        workerId = apiOrder.workerId,
                        name = "Placeholder Service",
                        enable = true,
                        estimatedTime = LocalTime.of(5, 0),
                        description = "Placeholder Description"
                    ),
                    status = OrderStatus.valueOf(apiOrder.status.uppercase()) // Ensure enum matches
                )
            }
            return response
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error fetching orders: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getOrderById(id: String): Order {
        val apiOrder = apiService.getOrderById(id)
        return Order(
            id = apiOrder.id,
            appointmentDate = LocalDateTime.ofInstant(
                Instant.parse(apiOrder.appointmentDate), ZoneId.systemDefault()),
            value = apiOrder.value,
            purchaseTime = LocalDateTime.ofInstant(
                Instant.parse(apiOrder.purchaseTime), ZoneId.systemDefault()),
            workerRating = apiOrder.workerRating,
            worker = Worker(
                id = apiOrder.workerId,
                firstName = "Placeholder",
                businessName = "Placeholder",
                lastName = "Placeholder",
                job = "Placeholder",
                email = "placeholder@example.com",
                phone = "1234567890",
                gender = "Male",
                profilePicUrl = "https://example.com/profile.jpg",
                isAvailable = true
            ),
            service = Service(
                id = apiOrder.serviceId,
                value = 50.0,
                workerId = apiOrder.workerId,
                name = "Placeholder Service",
                enable = true,
                estimatedTime = LocalTime.of(5, 0),
                description = "Placeholder Description"
            ),
            status = OrderStatus.valueOf(apiOrder.status.uppercase()) // Ensure enum matches
        )
    }
}
