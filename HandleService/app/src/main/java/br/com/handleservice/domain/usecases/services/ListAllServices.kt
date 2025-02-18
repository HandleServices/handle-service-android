package br.com.handleservice.domain.usecases.services

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import android.util.Log
import androidx.annotation.RequiresExtension
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetAllOrdersUseCase @Inject constructor(
    private val repository: OrdersRepository
) {
    operator fun invoke(): Flow<UiState<List<Order>>> = flow {
        emit(UiState.Loading())
            try {
                val orders = repository.getAllOrders()
                emit(UiState.Success(data = orders))
            } catch (e: IOException) {
                emit(UiState.Error(message = "Network error: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(UiState.Error(message = "Unexpected error: ${e.localizedMessage}"))
            }
    }
}