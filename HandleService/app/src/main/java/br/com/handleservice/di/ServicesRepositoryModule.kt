package br.com.handleservice.di

import br.com.handleservice.data.repository.OrdersRepositoryImpl
import br.com.handleservice.data.repository.ServicesRepositoryImpl
import br.com.handleservice.data.repository.WorkersRepositoryImpl
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.domain.repository.ServicesRepository
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
    abstract fun bindServicesRepository(
        servicesRepositoryImpl: ServicesRepositoryImpl
    ): ServicesRepository
}
