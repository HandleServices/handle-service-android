package br.com.handleservice.ui.mock

import br.com.handleservice.domain.model.Address

fun getMockAddress(): List<Address> {
    return listOf(
        Address(
            id = 1,
            type = "Usar minha localização atual",
            street = "R. que eu estou",
            neighborhood = "Bairro",
            number = 0,
            city = "Cidade",
            state = "Estado",
            note = "Via Google Maps",
            isSelected = false
        ),
        Address(
            id = 2,
            type = "Casa",
            street = "R. Franco Costa",
            neighborhood = "Alto da Lapa",
            number = 78,
            city = "São Paulo",
            state = "SP",
            note = "Próximo ao mercado central",
            isSelected = true
        ),
        Address(
            id = 3,
            type = "Trabalho",
            street = "R. Franco Costa",
            neighborhood = "Alto da Lapa",
            number = 78,
            city = "São Paulo",
            state = "SP",
            note = "Próximo ao mercado central",
            isSelected = false
        ),
        Address(
            id = 4,
            type = "Casa do Gustavo",
            street = "R. Franco Costa",
            neighborhood = "Alto da Lapa",
            number = 78,
            city = "São Paulo",
            state = "SP",
            note = "Próximo ao mercado central",
            isSelected = false
        )
    )
}
