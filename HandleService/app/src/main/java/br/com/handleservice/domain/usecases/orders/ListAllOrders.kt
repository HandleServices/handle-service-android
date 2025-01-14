package br.com.handleservice.domain.usecases.orders

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(
                Build.VERSION_CODES.S) >= 7) {
            try {
                val orders = repository.getAllOrders()
                emit(UiState.Success(data = orders))
            } catch (e: IOException) {
                emit(UiState.Error(message = "Network error: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(UiState.Error(message = "Server error: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(UiState.Error(message = "Unexpected error: ${e.localizedMessage}"))
            }
        }
    }
}