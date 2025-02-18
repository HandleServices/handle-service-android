package br.com.handleservice.domain.repository

import br.com.handleservice.data.model.Expedient
import br.com.handleservice.data.model.WorkerCreateDTO
import br.com.handleservice.data.model.WorkerUpdateDTO
import br.com.handleservice.domain.model.Worker
import retrofit2.http.Body

interface WorkersRepository {
    suspend fun getAllWorkers(): List<Worker>

    suspend fun getWorkerExpedient(id: Int): Expedient

    suspend fun getWorker(id: Int): Worker

    suspend fun getWorkerBySearch(search: String): List<Worker>
}