package br.com.handleservice.domain.usecases.services

import jakarta.inject.Inject

class ServicesUseCase @Inject constructor(
    val getServiceByWorkerIdUseCase: GetServiceByWorkerIdUseCase,
    val listAllServicesUseCase: GetAllServicesUseCase
) {

}