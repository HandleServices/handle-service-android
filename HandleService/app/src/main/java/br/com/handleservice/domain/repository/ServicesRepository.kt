package br.com.handleservice.domain.repository

import br.com.handleservice.domain.model.Service

interface ServicesRepository {
    suspend fun getAllWorkerServices(userId: Int): List<Service>

    suspend fun getService(serviceId: Int, userId: Int): Service
}