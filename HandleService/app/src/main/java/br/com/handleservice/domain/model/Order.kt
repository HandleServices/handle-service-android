package br.com.handleservice.domain.model

import java.time.LocalDateTime
import java.time.LocalTime

enum class OrderStatus {
    PENDING,
    IN_PROGRESS,
    FINISHED,
    CANCELED
}

data class Order (
    val id: Int,
    val appointmentDate: LocalDateTime,
    val value: Int,
    val purchaseTime: LocalDateTime,
    val workerRating: Int,
    val worker: Worker,
    val service: Service,
    val status: OrderStatus
)
