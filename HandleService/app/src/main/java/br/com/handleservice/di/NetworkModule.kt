package br.com.handleservice.di

import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.data.repository.OrdersImpl
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.util.Constants
import br.com.handleservice.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Ensure Gson is added
            .build()
    }

    @Provides
    fun provideOrdersApiService(retrofit: Retrofit): OrdersApiService {
        return retrofit.create(OrdersApiService::class.java)
    }

    @Provides
    fun provideOrdersRepository(apiService: OrdersApiService): OrdersRepository {
        return OrdersImpl(apiService)
    }
}
