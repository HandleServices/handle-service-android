package br.com.handleservice.domain.usecases.services

import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.repository.ServicesRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetAllServicesUseCase @Inject constructor(
    private val repository: ServicesRepository
) {
    operator fun invoke(id: Int): Flow<UiState<List<Service>>> = flow {
        emit(UiState.Loading())
            try {
                val orders = repository.getAllWorkerServices(id)
                emit(UiState.Success(data = orders))
            } catch (e: IOException) {
                emit(UiState.Error(message = "Network error: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(UiState.Error(message = "Unexpected error: ${e.localizedMessage}"))
            }
    }
}