package br.com.handleservice.domain.usecases.workers

import jakarta.inject.Inject

class WorkersUseCase @Inject constructor(
    val getWorkerByIdUseCase: GetWorkerByIdUseCase,
    val listAllWorkersUseCase: GetAllWorkersUseCase
) {

}