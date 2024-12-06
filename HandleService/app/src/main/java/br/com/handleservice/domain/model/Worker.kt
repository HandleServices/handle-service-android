package br.com.handleservice.domain.model

data class Worker (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val businessName: String,
    val phone: String,
    val profilePicUrl: String
)