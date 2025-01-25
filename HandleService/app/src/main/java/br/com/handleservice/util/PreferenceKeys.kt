package br.com.handleservice.util

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val ANIMATIONS_ENABLED = booleanPreferencesKey("animations_enabled")
}
