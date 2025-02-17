package br.com.handleservice.presentation.screens.chat

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.handleservice.presentation.screens.chat.components.ChatContactList
data class ChatContact(val chatId: Int, val name: String, val imageUrl: String)

/**
val mockContacts = getMockWorker().take(2).mapIndexed { index, worker ->
    ChatContact(chatId = 1, name = worker.businessName, imageUrl = worker.profilePicUrl)
}
**/
val mockContacts = listOf(
    ChatContact(chatId = 1, name = "Prestador de Serviço", imageUrl = "worker_profile_url")
)

@Composable
fun ChatScreen(navController: NavController) {
    ChatContactList(contacts = mockContacts, navController = navController)
}
