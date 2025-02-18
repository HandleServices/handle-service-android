package br.com.handleservice.data.model

data class OrderCreateDTO(
    val appointmentDate: String,
    val value: Double,
    val serviceId: Int,
    val workerId: Int,
    val clientId: Int
)