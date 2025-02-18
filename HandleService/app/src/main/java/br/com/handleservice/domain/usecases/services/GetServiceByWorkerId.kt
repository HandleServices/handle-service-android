package br.com.handleservice.domain.usecases.services

import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.domain.repository.ServicesRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetServiceByIdUseCase @Inject constructor(private val repositoryImpl : ServicesRepository)  {
    operator fun invoke(id: Int): Flow<UiState<Service>> = flow {
        emit(UiState.Loading())
        try {
            val service = repositoryImpl.getService(id)
            emit(UiState.Success(data = service))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}