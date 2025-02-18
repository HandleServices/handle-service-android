package br.com.handleservice.di

import br.com.handleservice.data.repository.WorkersRepositoryImpl
import br.com.handleservice.domain.repository.WorkersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkersRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkersRepository(
        workersRepositoryImpl: WorkersRepositoryImpl
    ): WorkersRepository
}
