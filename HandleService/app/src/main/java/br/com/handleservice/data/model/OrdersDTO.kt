package br.com.handleservice.data.model

data class OrdersDTO(
    val id: Int,
    val appointmentDate: String,
    val value: Double,
    val purchaseTime: String,
    val serviceId: Int,
    val workerId: Int,
    val workerRating: Int,
    val status: String
)
