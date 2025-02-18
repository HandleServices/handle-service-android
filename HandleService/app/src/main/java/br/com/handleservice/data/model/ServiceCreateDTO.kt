package br.com.handleservice.data.model

data class ServiceCreateDTO(
    val description: String? = null,
    val enable: Boolean? = null,
    val estimatedTime: String? = null,
    val name: String? = null,
    val value: Double? = null,
    val workerId: Int? = null
)