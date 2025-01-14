package br.com.handleservice.di

import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.data.repository.OrdersRepository
import br.com.handleservice.util.Constants
import br.com.handleservice.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Configura o nível de logging para o corpo das requisições/respostas
        return loggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)  // Adiciona o interceptor de logging
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Configura o Retrofit para usar o OkHttp com logging
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOrdersApiService(retrofit: Retrofit): OrdersApiService {
        return retrofit.create(OrdersApiService::class.java)
    }

    @Provides
    fun provideOrdersRepository(apiService: OrdersApiService): OrdersRepository {
        return OrdersRepository(apiService)
    }
}
