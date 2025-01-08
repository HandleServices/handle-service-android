package br.com.handleservice.ui.mock

import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import java.time.LocalDateTime
import java.time.LocalTime

fun getMockOrders(): List<Order> {
    return listOf(
        Order(
            id = 1,
            appointmentDate = LocalDateTime.now(),
            value = 100,
            purchaseTime = LocalDateTime.now(),
            workerRating = 5,
            worker = Worker(
                id = 1,
                firstName = "Gabriel",
                lastName = "Al-Samir",
                gender = "Masculino",
                email = "alsamir@teste.com",
                businessName = "Eletricista Moraes",
                phone = "(63) 0000-0000",
                job = "Encanador",
                profilePicUrl = "https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg",
                isAvailable = true
            ),
            service = Service(
                id = 1,
                enable = true,
                value = 100,
                name = "Troca de pia",
                description = "",
                workerId = 1,
                estimatedTime = LocalTime.now(),
            ),
            status = OrderStatus.IN_PROGRESS
        ),
        Order(
            id = 2,
            appointmentDate = LocalDateTime.now().plusDays(1),
            value = 200,
            purchaseTime = LocalDateTime.now(),
            workerRating = 4,
            worker = Worker(
                id = 1,
                firstName = "Gabriel",
                lastName = "Al-Samir",
                gender = "Masculino",
                email = "alsamir@teste.com",
                businessName = "Eletricista Moraes",
                phone = "(63) 0000-0000",
                job = "Encanador",
                profilePicUrl = "https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg",
                isAvailable = false
            ),
            service = Service(
                id = 1,
                enable = true,
                value = 100,
                name = "Troca de pia",
                description = "",
                workerId = 1,
                estimatedTime = LocalTime.now(),
            ),
            status = OrderStatus.FINISHED
        )
    )
}