package br.com.handleservice.domain.repository

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.Service
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WorkersRepository {
    suspend fun getAllServices(userId: Int): List<Service>

    suspend fun getService(id: Int): Service

    suspend fun editService(id: Int, orderUpdate: OrderUpdateDTO): Service

    suspend fun deleteService(id: Int): String

    suspend fun createService(orderCreateDTO: OrderCreateDTO): Service
}