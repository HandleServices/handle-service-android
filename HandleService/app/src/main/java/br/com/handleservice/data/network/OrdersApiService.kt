package br.com.handleservice.data.network

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.data.model.OrdersDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrdersApiService {
    @GET("api/v1/orders/all/0")
    suspend fun getAllOrders(): List<OrdersDTO>

    @GET("api/v1/orders/{id}")
    suspend fun getOrderById(@Path("id") id: String): OrdersDTO

    @PUT("api/v1/orders/{id}")
    suspend fun editOrder(@Path("id") id: String, @retrofit2.http.Body orderUpdate: OrderUpdateDTO): OrdersDTO

    @DELETE("api/v1/orders/{id}")
    suspend fun deleteOrder(@Path("id") id: String): String

    @POST("api/v1/orders")
    suspend fun createOrder(@retrofit2.http.Body orderCreateDTO: OrderCreateDTO): OrdersDTO
}
