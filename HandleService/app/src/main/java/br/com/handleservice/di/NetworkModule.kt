package br.com.handleservice.di

import br.com.handleservice.data.network.OrdersApiService
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



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOrdersApiService(retrofit: Retrofit): OrdersApiService {
        return retrofit.create(OrdersApiService::class.java)
    }

    @Provides
    fun provideOrdersRepository(apiService: OrdersApiService): OrdersRepositoryImpl {
        return OrdersRepositoryImpl(apiService)
    }
}
