package br.com.handleservice.domain.model

import br.com.handleservice.util.LocalTimeAdapter
import com.google.gson.annotations.JsonAdapter
import java.time.LocalTime

data class Service(
    val id: Int,
    val enable: Boolean,
    val value: Double,
    val name: String,
    @JsonAdapter(LocalTimeAdapter::class)
    val estimatedTime: LocalTime,
    val description: String,
    val workerId: Int
)