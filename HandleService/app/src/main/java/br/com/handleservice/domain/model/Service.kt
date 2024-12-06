package br.com.handleservice.domain.model

import java.time.LocalTime

data class Service (
    val id: Int,
    val enable: Boolean,
    val value: Int,
    val name: String,
    val estimatedTime: LocalTime,
    val description: String,
    val workerId: Int
)