package br.com.handleservice.data.model

data class OrdersDTO(
    val id: Int,
    val appointmentDate: String, // Matches "appointmentDate" in JSON
    val value: Double, // Can handle both integers and decimals
    val purchaseTime: String, // Matches "purchaseTime" in JSON
    val serviceId: Int,
    val workerId: Int,
    val workerRating: Int,
    val status: String // Matches "status" in JSON
)
