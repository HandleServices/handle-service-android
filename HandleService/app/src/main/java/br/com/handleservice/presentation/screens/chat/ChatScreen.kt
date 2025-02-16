package br.com.handleservice.presentation.screens.chat

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.handleservice.presentation.screens.chat.components.ChatContactList
import br.com.handleservice.ui.mock.getMockWorker

// Modelo de contato
data class ChatContact(val id: Int, val name: String, val imageUrl: String)

val mockContacts = getMockWorker().take(2).map {
    ChatContact(it.id, it.businessName, it.profilePicUrl)
}

@Composable
fun ChatScreen(navController: NavController) {
    ChatContactList(contacts = mockContacts, navController = navController)
}

