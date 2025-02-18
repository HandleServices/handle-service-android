package br.com.handleservice.domain.usecases.workers

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import android.util.Log
import androidx.annotation.RequiresExtension
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.domain.repository.ServicesRepository
import br.com.handleservice.domain.repository.WorkersRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetAllWorkersUseCase @Inject constructor(
    private val repository: WorkersRepository
) {
    operator fun invoke(): Flow<UiState<List<Worker>>> = flow {
        emit(UiState.Loading())
            try {
                val orders = repository.getAllWorkers()
                emit(UiState.Success(data = orders))
            } catch (e: IOException) {
                emit(UiState.Error(message = "Network error: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(UiState.Error(message = "Unexpected error: ${e.localizedMessage}"))
            }
    }
}