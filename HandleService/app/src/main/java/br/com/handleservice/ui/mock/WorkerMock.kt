package br.com.handleservice.ui.mock

import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import java.time.LocalDateTime
import java.time.LocalTime

fun getMockWorker(): Sequence<Worker> {
    return sequenceOf(
        Worker(
            id = 1,
            firstName = "Jocélio",
            lastName = "Andrade de Souza",
            gender = "",
            email = "jocélio@gmail.com",
            businessName = "Encanamentos & Cia",
            job = "Encanador", // trocado job por category
            phone = "(63) 00000-0000",
            profilePicUrl = "https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg",
            isAvailable = false
        )
    )
}