package br.com.handleservice.data

import br.com.handleservice.presentation.screens.chat.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ChatWebSocketManager(private val chatId: Int, private val username: String) {
    private val wsBaseUrl = "ws://handle-api-1017711936653.us-central1.run.app/api/v1"
    private var webSocket: WebSocket? = null

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    fun connect() {
        val request = Request.Builder()
            .url("$wsBaseUrl/chats/clients/$username")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("WebSocket conectado!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val json = JSONObject(text)
                val type = json.getString("type")

                if (type == "message") {
                    val payload = json.getJSONObject("payload").getJSONObject("message")
                    val message = Message(
                        content = payload.getString("content"),
                        from = payload.getString("from"),
                        time = payload.optString("sentAt", "")
                    )
                    _messages.update { it + message }
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                println("Mensagem binária recebida: $bytes")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("Erro no WebSocket: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("WebSocket fechado: $reason")
            }
        })
    }

    fun sendMessage(message: String) {
        val json = JSONObject()
        json.put("content", message)
        json.put("from", username)
        json.put("to", chatId)

        webSocket?.send(json.toString())
        _messages.update { it + Message(content = message, from = "1", time = "") }
    }

    fun disconnect() {
        webSocket?.close(1000, "Usuário saiu")
    }
}
