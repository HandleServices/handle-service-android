package br.com.handleservice.data.model

data class WorkerUpdateDTO(
    val businessName: String? = null,
    val docNum: String? = null,
    val docType: String? = null,
    val email: String? = null,
    val expedient: Expedient? = null,
    val firstName: String? = null,
    val gender: String? = null,
    val jobId: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    val profilePicUrl: String? = null
)
