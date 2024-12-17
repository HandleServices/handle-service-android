package br.com.handleservice.presentation.screens.notification

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationViewModel : ViewModel() {

    private val _notifications = MutableStateFlow(
        listOf(
            Notification(
                title = "Mensagem nova!",
                description = "João Mário Encanamentos acabou de te enviar uma mensagem no chat. Saiba mais...",
                time = "10:30"
            ),
            Notification(
                title = "Serviço encerrado, hora de avaliar!",
                description = "Parece que João Mário Encanamentos acabou de finalizar seu atendimento, que tal avaliar o serviço prestado?",
                time = "10:30"
            ),
            Notification(
                title = "Vai uma mãozinha aí?",
                description = "Faz tempo que você não contrata a gente... tem certeza que não precisa de nada por aí?",
                time = "10:30"
            ),
            Notification(
                title = "Contratação agendada!",
                description = "No dia 12/10, às 14:10h, João Mário Encanamentos passará aí pra te dar uma mãozinha viu?",
                time = "10:30"
            )
        )
    )

    val notifications: StateFlow<List<Notification>> = _notifications
}

data class Notification(
    val title: String,
    val description: String,
    val time: String
)
