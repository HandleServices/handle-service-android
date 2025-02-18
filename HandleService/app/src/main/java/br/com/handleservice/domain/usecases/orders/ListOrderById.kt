package br.com.handleservice.domain.usecases.orders

import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.repository.OrdersRepository
import br.com.handleservice.ui.components.loading.UiState
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetOrderByIdUseCase @Inject constructor(private val repositoryImpl : OrdersRepository)  {
    operator fun invoke(id: Int): Flow<UiState<Order>> = flow {
        emit(UiState.Loading())
        try {
            val order = repositoryImpl.getOrderById(id)
            emit(UiState.Success(data = order))
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}