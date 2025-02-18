package br.com.handleservice.data.repository

import android.util.Log
import br.com.handleservice.data.model.ServiceCreateDTO
import br.com.handleservice.data.model.ServiceUpdateDTO
import br.com.handleservice.data.network.ServicesApiService
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.repository.ServicesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServicesRepositoryImpl @Inject constructor(
    private val apiService: ServicesApiService
) : ServicesRepository {

    override suspend fun getAllWorkerServices(userId: Int): List<Service> {
        return try {
            apiService.getAllWorkerServices(userId)
        } catch (e: Exception) {
            Log.e("ServicesRepositoryImpl", "Error fetching services for worker $userId: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getService(serviceId: Int, userId: Int): Service {
        return try {
            apiService.getService(serviceId, userId)
        } catch (e: Exception) {
            Log.e("ServicesRepositoryImpl", "Error fetching service with id $serviceId: ${e.localizedMessage}")
            throw e
        }
    }
}
