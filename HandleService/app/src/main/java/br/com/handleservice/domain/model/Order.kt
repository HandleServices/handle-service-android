package br.com.handleservice.domain.model

import LocalDateTimeAdapter
import br.com.handleservice.util.LocalTimeAdapter
import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

enum class OrderStatus {
    PENDING,
    IN_PROGRESS,
    FINISHED,
    CANCELLED,
    SCHEDULED
}

data class Order (
    val id: Int,
    @JsonAdapter(LocalDateTimeAdapter::class)
    val appointmentDate: LocalDateTime,
    val value: Double,
    @JsonAdapter(LocalDateTimeAdapter::class)
    val purchaseTime: LocalDateTime,
    val workerRating: Int,
    val workerId: Int,
    val serviceId: Int,
    val status: OrderStatus
)
