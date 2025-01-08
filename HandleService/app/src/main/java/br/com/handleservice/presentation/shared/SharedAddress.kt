package br.com.handleservice.presentation.shared

import androidx.lifecycle.ViewModel
import br.com.handleservice.domain.model.Address
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SharedAddressViewModel @Inject constructor() : ViewModel() {
    private val _selectedAddress = MutableStateFlow<Address?>(null)
    val selectedAddress: StateFlow<Address?> = _selectedAddress

    fun updateSelectedAddress(address: Address) {
        _selectedAddress.value = address
    }
}