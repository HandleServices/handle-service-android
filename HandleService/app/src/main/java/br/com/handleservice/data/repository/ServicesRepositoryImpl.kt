package br.com.handleservice.data.repository

import android.util.Log
import br.com.handleservice.data.model.Expedient
import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.data.model.WorkerCreateDTO
import br.com.handleservice.data.model.WorkerUpdateDTO
import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.data.network.WorkersApiService
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.domain.repository.WorkersRepository
import br.com.handleservice.ui.mock.getMockServices
import br.com.handleservice.ui.mock.getMockWorker
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Singleton
class ServicesRepositoryImpl @Inject constructor(
    private val apiService: WorkersApiService
) : WorkersRepository {

    override suspend fun getAllWorkers(): List<Worker> {
        return try {
            apiService.getAllWorkers()
        } catch (e: Exception) {
            Log.e("WorkersRepository", "Error fetching all workers: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getWorkerExpedient(id: Int): Expedient {
        return try {
            apiService.getWorkerExpedient(id)
        } catch (e: Exception) {
            Log.e("WorkersRepository", "Error fetching worker expedient: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getWorker(id: Int): Worker {
        return try {
            apiService.getWorker(id)
        } catch (e: Exception) {
            Log.e("WorkersRepository", "Error fetching worker: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getWorkerBySearch(search: String): List<Worker> {
        return try {
            apiService.getWorkerBySearch(search)
        } catch (e: Exception) {
            Log.e("WorkersRepository", "Error searching workers: ${e.localizedMessage}")
            throw e
        }
    }

}
