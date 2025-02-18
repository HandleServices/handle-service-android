package br.com.handleservice.presentation.screens.worker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.domain.repository.ServicesRepository
import br.com.handleservice.domain.repository.WorkersRepository
import br.com.handleservice.ui.mock.getMockServices
import br.com.handleservice.ui.mock.getMockWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val workersRepository: WorkersRepository,
    private val servicesRepository: ServicesRepository
) : ViewModel() {

    private val _worker = MutableLiveData<Worker>()
    val worker: LiveData<Worker> = _worker

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services

    fun loadWorkerById(workerId: Int) {
        viewModelScope.launch {
            try {
                val foundWorker = workersRepository.getWorker(workerId)
                _worker.value = foundWorker
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadServicesForWorker(workerId: Int) {
        viewModelScope.launch {
            try {
                val servicesList = servicesRepository.getAllWorkerServices(workerId)
                _services.value = servicesList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
