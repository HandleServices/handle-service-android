package br.com.handleservice.data.network

import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.data.model.OrderUpdateDTO
import br.com.handleservice.domain.model.Order
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrdersApiService {
    @GET("api/v1/orders/all/1")
    suspend fun getAllOrders(): List<Order>

    @GET("api/v1/orders/{id}")
    suspend fun getOrderById(@Path("id") id: Int): Order

    @PUT("api/v1/orders/{id}")
    suspend fun editOrder(@Path("id") id: Int, @retrofit2.http.Body orderUpdate: OrderUpdateDTO): Order

    @DELETE("api/v1/orders/{id}")
    suspend fun deleteOrder(@Path("id") id: Int): String

    @POST("api/v1/orders")
    suspend fun createOrder(@retrofit2.http.Body orderCreateDTO: OrderCreateDTO): Order
}
