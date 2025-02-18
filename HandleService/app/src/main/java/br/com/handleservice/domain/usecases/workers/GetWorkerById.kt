package br.com.handleservice.domain.usecases.workers

import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.domain.repository.ServicesRepository
import br.com.handleservice.domain.repository.WorkersRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetWorkerByIdUseCase @Inject constructor(private val repositoryImpl : WorkersRepository)  {
    operator fun invoke(id: Int): Flow<UiState<Worker>> = flow {
        emit(UiState.Loading())
        try {
            val worker = repositoryImpl.getWorker(id)
            emit(UiState.Success(data = worker))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}