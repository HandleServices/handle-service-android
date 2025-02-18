package br.com.handleservice.data.model

data class Expedient(
    val startTime: String,
    val endTime: String,
    val weekDay: String
)

data class WorkerCreateDTO(
    val businessName: String,
    val docNum: String,
    val docType: String,
    val email: String,
    val expedient: Expedient,
    val firstName: String,
    val gender: String,
    val jobId: String,
    val lastName: String,
    val phone: String,
    val profilePicUrl: String
)