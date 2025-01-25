package br.com.handleservice.di

import android.app.Application
import android.content.Context
import br.com.handleservice.data.manager.LocalUserManagerImpl
import br.com.handleservice.domain.manager.LocalUserManager
import br.com.handleservice.domain.usecases.entry.AppEntryUseCases
import br.com.handleservice.domain.usecases.entry.ReadAppEntry
import br.com.handleservice.domain.usecases.entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Context): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )
}