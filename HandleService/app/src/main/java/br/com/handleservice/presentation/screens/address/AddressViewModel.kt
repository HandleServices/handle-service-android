package br.com.handleservice.presentation.screens.address

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handleservice.domain.model.Address
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.ui.mock.getMockAddress
import br.com.handleservice.ui.mock.getMockServices
import br.com.handleservice.ui.mock.getMockWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor() : ViewModel() {
    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> = _addresses

    private val _selectedAddress = MutableStateFlow<Address?>(addresses.value.find { it.isSelected })
    val selectedAddress: StateFlow<Address?> = _selectedAddress

    init {
        loadAddress()
    }

    private fun loadAddress() {
        viewModelScope.launch {
            _addresses.value = getMockAddress()
        }
    }

    fun toggleAddressSelection(address: Address) {
        viewModelScope.launch {
            _addresses.value = _addresses.value.map { it.copy(isSelected = it.id == address.id) }
            _selectedAddress.value = address
        }
    }
}
