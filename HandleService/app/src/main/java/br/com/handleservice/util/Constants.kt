package br.com.handleservice.util

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val USER_SETTINGS = "userSettings"
    const val APP_ENTRY = "appEntry"
    const val BASE_URL = "https://handle-api-1017711936653.us-central1.run.app/"
  
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}