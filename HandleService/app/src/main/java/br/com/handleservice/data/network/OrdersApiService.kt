package br.com.handleservice.data.network

import br.com.handleservice.data.model.OrdersDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface OrdersApiService {
    @GET("api/v1/orders/all/0")
    suspend fun getAllOrders(): List<OrdersDTO>

    @GET("api/v1/orders/0/{Id}")
    suspend fun getOrderById(@Path("Id") Id : String): OrdersDTO
}