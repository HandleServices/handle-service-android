package br.com.handleservice.domain.model

data class Worker (
    val id: Int,
    val firstName: String,
    val lastName: String,
    // TO-DO: Use it just after config gender in web and backend (enum?)
    val gender: String,
    val email: String,
    val businessName: String,
    val phone: String,
    // TO-DO: See how it will be config on back (enum?)
    val job: String,
    val profilePicUrl: String
)