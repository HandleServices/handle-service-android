package br.com.handleservice.di.controllers

import br.com.handleservice.data.repository.OrdersImpl
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.domain.usecases.orders.GetAllOrdersUseCase
import br.com.handleservice.domain.usecases.orders.GetOrderByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrdersUseCaseModule {

    @Provides
    fun provideGetAllOrdersUseCase(repository: OrdersRepository): GetAllOrdersUseCase {
        return GetAllOrdersUseCase(repository)
    }

    @Provides
    fun provideGetOrderByIdUseCase(repository: OrdersRepository): GetOrderByIdUseCase {
        return GetOrderByIdUseCase(repository)
    }
}
