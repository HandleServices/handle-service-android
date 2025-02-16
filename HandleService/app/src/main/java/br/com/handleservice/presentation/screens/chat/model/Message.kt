package br.com.handleservice.presentation.screens.chat.model

data class Message(
    val text: String,
    val time: String,
    val isSent: Boolean
)