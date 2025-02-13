package br.com.handleservice.data.repository

import android.util.Log
import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.data.model.OrdersDTO
import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.ui.mock.getMockServices
import br.com.handleservice.ui.mock.getMockWorker
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Singleton
class OrdersRepositoryImpl @Inject constructor(
    private val apiService: OrdersApiService
) : OrdersRepository {

    private val workers: List<Worker> = getMockWorker().toList()
    private val services: List<Service> = getMockServices().toList()

    override suspend fun getAllOrders(): List<Order> {
        try {
            val response = apiService.getAllOrders().map { apiOrder ->
                val actualWorker = workers.random()
                val filteredServices = services.filter { it.workerId == actualWorker.id }
                val service = filteredServices.ifEmpty { services }.random()

                Order(
                    id = apiOrder.id,
                    appointmentDate = LocalDateTime.ofInstant(
                        Instant.parse(apiOrder.appointmentDate), ZoneId.systemDefault()
                    ),
                    value = apiOrder.value,
                    purchaseTime = LocalDateTime.ofInstant(
                        Instant.parse(apiOrder.purchaseTime), ZoneId.systemDefault()
                    ),
                    workerRating = apiOrder.workerRating,
                    worker = actualWorker,
                    service = service,
                    status = OrderStatus.valueOf(apiOrder.status.uppercase())
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
                Instant.parse(apiOrder.appointmentDate), ZoneId.systemDefault()
            ),
            value = apiOrder.value,
            purchaseTime = LocalDateTime.ofInstant(
                Instant.parse(apiOrder.purchaseTime), ZoneId.systemDefault()
            ),
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
            status = OrderStatus.valueOf(apiOrder.status.uppercase())
        )
    }

    override suspend fun editOrder(id: String, orderUpdate: OrderUpdateDTO): OrdersDTO {
        return try {
            apiService.editOrder(id, orderUpdate)
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error editing order: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun deleteOrder(id: String): String {
        return try {
            apiService.deleteOrder(id)
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error deleting order: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun createOrder(orderCreateDTO: OrderCreateDTO): OrdersDTO {
        return try {
            apiService.createOrder(orderCreateDTO)
        } catch (e: Exception) {
            Log.e("OrdersRepository", "Error creating order: ${e.localizedMessage}")
            throw e
        }
    }
}
