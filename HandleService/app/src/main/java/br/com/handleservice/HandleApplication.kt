package br.com.handleservice

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")

@HiltAndroidApp
class HandleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
