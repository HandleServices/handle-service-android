package br.com.handleservice.di

import br.com.handleservice.data.repository.OrdersRepositoryImpl
import br.com.handleservice.data.repository.WorkersRepositoryImpl
import br.com.handleservice.domain.repository.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkersRepository(
        workersRepositoryImpl: WorkersRepositoryImpl
    ): OrdersRepository
}
