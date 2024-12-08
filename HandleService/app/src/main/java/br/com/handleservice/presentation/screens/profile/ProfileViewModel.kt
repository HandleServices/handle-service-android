package br.com.handleservice.presentation.screens.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserProfile(
    val name: String,
    val photoUrl: String?,
    val chatHistory: Boolean,
    val addresses: Boolean,
    val accountDetails: Boolean,
    val appSettings: Boolean
)

class ProfileViewModel : ViewModel() {
    private val _profileState = MutableStateFlow(
        UserProfile(
            name = "Gabriela Almeida Guimarães Silva",
            photoUrl = null,
            chatHistory = true,
            addresses = true,
            accountDetails = true,
            appSettings = true
        )
    )
    val profileState: StateFlow<UserProfile> = _profileState
}
