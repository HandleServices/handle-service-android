package br.com.handleservice.presentation.screens.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import br.com.handleservice.util.PreferencesKeys
import br.com.handleservice.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val dataStore = context.dataStore

    val darkModeEnabled: StateFlow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_MODE] ?: false
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val notificationsEnabled: StateFlow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true
        }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val animationsEnabled: StateFlow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.ANIMATIONS_ENABLED] ?: true // Ativado por padrão
        }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun toggleDarkMode(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.DARK_MODE] = isEnabled
            }
        }
    }

    fun toggleNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = isEnabled
            }
        }
    }

    fun toggleAnimations(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.ANIMATIONS_ENABLED] = isEnabled
            }
        }
    }

}

