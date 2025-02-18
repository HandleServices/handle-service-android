package br.com.handleservice.domain.usecases.services

import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.repository.ServicesRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetServiceByWorkerIdUseCase @Inject constructor(private val repositoryImpl : ServicesRepository)  {
    operator fun invoke(serviceId: Int, userId: Int): Flow<UiState<Service>> = flow {
        emit(UiState.Loading())
        try {
            val service = repositoryImpl.getService(serviceId, userId)
            emit(UiState.Success(data = service))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}