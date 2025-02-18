package br.com.handleservice.data.model

data class ServiceCreateDTO(
    val description: String,
    val enable: Boolean,
    val estimatedTime: String,
    val name: String,
    val value: Double,
    val workerId: Int
)