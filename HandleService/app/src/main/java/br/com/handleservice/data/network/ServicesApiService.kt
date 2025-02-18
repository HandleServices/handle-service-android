package br.com.handleservice.data.network

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.domain.model.Service
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ServicesApiService {
    @GET("api/v1/services/all/{userId}")
    suspend fun getAllWorkerServices(@Path("userId") userId: Int): List<Service>

    @GET("api/v1/services/{serviceId}")
    suspend fun getService(
        @Path("serviceId") serviceId: Int,
        @Query("userId") userId: Int
    ): Service

    @PUT("api/v1/services/{id}")
    suspend fun editService(
        @Path("id") id: Int,
        @retrofit2.http.Body orderUpdate: OrderUpdateDTO
    ): Service

    @DELETE("api/v1/services/{id}")
    suspend fun deleteService(@Path("id") id: Int): String

    @POST("api/v1/services")
    suspend fun createService(@retrofit2.http.Body orderCreateDTO: OrderCreateDTO): Service
}
