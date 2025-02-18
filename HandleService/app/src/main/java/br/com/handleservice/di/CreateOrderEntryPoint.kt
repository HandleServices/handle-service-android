package br.com.handleservice.di

import br.com.handleservice.domain.usecases.orders.CreateOrderUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CreateOrderEntryPoint {
    fun createOrderUseCase(): CreateOrderUseCase
}
