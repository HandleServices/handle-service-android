package br.com.handleservice.data.network

import br.com.handleservice.data.model.Expedient
import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.data.model.WorkerCreateDTO
import br.com.handleservice.data.model.WorkerUpdateDTO
import br.com.handleservice.domain.model.Worker
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WorkersApiService {
    @GET("api/v1/workers/all")
    suspend fun getAllWorkers(): List<Worker>

    @GET("api/v1/workers/expedient/{id}")
    suspend fun getWorkerExpedient(@Path("id") id: Int): Expedient

    @GET("api/v1/workers/{id}")
    suspend fun getWorker(@Path("id") id: Int): Worker

    @GET("api/v1/workers/search/{search}")
    suspend fun getWorkerBySearch(@Path("search") search: String): List<Worker>

    @PUT("api/v1/workers/expedient/{id}")
    suspend fun editWorkerExpedient(@Path("id") id: Int): Worker

    @PUT("api/v1/workers/{id}")
    suspend fun editWorker(@Path("id") id: Int, @retrofit2.http.Body workerUpdateDTO: WorkerUpdateDTO): Worker

    @DELETE("api/v1/workers/{id}")
    suspend fun deleteWorker(@Path("id") id: Int): String

    @POST("api/v1/workers")
    suspend fun createWorker(@retrofit2.http.Body workerCreateDTO: WorkerCreateDTO): Worker
}
