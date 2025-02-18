package br.com.handleservice.di

import br.com.handleservice.data.network.OrdersApiService
import br.com.handleservice.data.network.ServicesApiService
import br.com.handleservice.data.network.WorkersApiService
import br.com.handleservice.data.repository.OrdersRepositoryImpl
import br.com.handleservice.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOrdersApiService(retrofit: Retrofit): OrdersApiService {
        return retrofit.create(OrdersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(ordersApiService: OrdersApiService, workersApiService: WorkersApiService, servicesApiService: ServicesApiService): OrdersRepositoryImpl {
        return OrdersRepositoryImpl(
            ordersApiService,
        )
    }

    @Provides
    @Singleton
    fun provideWorkersApiService(retrofit: Retrofit): WorkersApiService {
        return retrofit.create(WorkersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideServicesApiService(retrofit: Retrofit): ServicesApiService {
        return retrofit.create(ServicesApiService::class.java)
    }
}
