package br.com.handleservice.data.model

data class OrderUpdateDTO(
    val appointmentDate: String?,
    val value: Double?,
    val serviceId: Int?,
    val workerId: Int?,
    val status: String?
)