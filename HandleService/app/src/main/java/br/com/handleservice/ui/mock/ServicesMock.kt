package br.com.handleservice.ui.mock

import br.com.handleservice.domain.model.Service
import java.time.LocalTime

fun getMockServices(): List<Service> {
    return listOf(
        Service(
            id = 1,
            enable = true,
            value = 150,
            name = "Reparo de Cano Quebrado",
            estimatedTime = LocalTime.of(2, 0),
            description = "Serviço de reparo para canos quebrados, incluindo substituição de peças e vedação de vazamentos.",
            workerId = 1
        ),
        Service(
            id = 2,
            enable = true,
            value = 200,
            name = "Desentupimento de Esgoto",
            estimatedTime = LocalTime.of(1, 30),
            description = "Desentupimento de canos e esgotos utilizando ferramentas profissionais para resolver obstruções.",
            workerId = 1
        ),
        Service(
            id = 3,
            enable = true,
            value = 300,
            name = "Instalação de Aquecedor a Gás",
            estimatedTime = LocalTime.of(3, 0),
            description = "Instalação completa de aquecedor a gás, garantindo segurança e funcionamento eficiente.",
            workerId = 1
        )
    )
}
