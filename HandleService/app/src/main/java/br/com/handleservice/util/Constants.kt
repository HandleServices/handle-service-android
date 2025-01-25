package br.com.handleservice.util

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val USER_SETTINGS = "userSettings"
    const val APP_ENTRY = "appEntry"
    const val BASE_URL = "http://10.0.2.2:7001"

    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}