package br.com.handleservice.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled

    fun toggleNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            _notificationsEnabled.value = isEnabled
        }
    }

    fun toggleDarkMode(isEnabled: Boolean) {
        viewModelScope.launch {
            _darkModeEnabled.value = isEnabled
        }
    }
}
