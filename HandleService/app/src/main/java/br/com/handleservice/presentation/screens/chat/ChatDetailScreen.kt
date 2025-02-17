package br.com.handleservice.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.chat.components.ChatBubble
import br.com.handleservice.presentation.screens.chat.components.ChatInputField
import br.com.handleservice.presentation.screens.chat.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatDetailScreen(navController: NavController, chatId: Int, userName: String) {
    val clientUsername = "1"
    val role = "clients"

    var messageText by remember { mutableStateOf(TextFieldValue()) }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val socketClient = remember { OkHttpClient() }
    var webSocket: WebSocket? by remember { mutableStateOf(null) }

    LaunchedEffect(chatId) {
        val url = "https://handle-api-1017711936653.us-central1.run.app/api/v1/chats/channels/messages/$chatId"
        try {
            val request = Request.Builder().url(url).build()
            val response = withContext(Dispatchers.IO) {
                socketClient.newCall(request).execute()
            }

            response.body?.string()?.let { jsonString ->
                val jsonArray = JSONArray(jsonString)
                val fetchedMessages = mutableListOf<Message>()

                for (i in 0 until jsonArray.length()) {
                    val jsonMessage = jsonArray.getJSONObject(i)
                    fetchedMessages.add(
                        Message(
                            content = jsonMessage.getString("content"),
                            from = jsonMessage.getString("from"),
                            time = jsonMessage.getString("sentAt")
                        )
                    )
                }

                messages = fetchedMessages

                coroutineScope.launch {
                    if (messages.isNotEmpty()) {
                        listState.scrollToItem(messages.size - 1)
                    }
                }
            }
        } catch (e: Exception) {
            println("❌ Erro ao buscar histórico: ${e.message}")
        }

        val wsRequest = Request.Builder()
            .url("ws://handle-api-1017711936653.us-central1.run.app/api/v1/chats/$role/$clientUsername/")
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("✅ Conectado ao WebSocket no chat $chatId")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("📩 Mensagem recebida do WebSocket: $text")
                val json = JSONObject(text)
                if (json.getString("type") == "message") {
                    val payload = json.getJSONObject("payload").getJSONObject("message")
                    val receivedMessage = Message(
                        content = payload.getString("content"),
                        from = payload.getString("from"),
                        time = payload.getString("sentAt")
                    )
                    messages = messages + receivedMessage

                    coroutineScope.launch {
                        if (messages.isNotEmpty()) {
                            listState.scrollToItem(messages.size - 1)
                        }
                    }
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("❌ Erro WebSocket: ${t.message}")
            }
        }

        webSocket = socketClient.newWebSocket(wsRequest, listener)
    }


    DisposableEffect(Unit) {
        onDispose {
            webSocket?.close(1000, "Saindo do chat")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(101.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Voltar",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() },
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Online",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(17.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(messages) { message ->
                ChatBubble(message, currentUserRole = role)
            }
        }

        ChatInputField(
            messageText = messageText,
            onMessageChange = { messageText = it },
            onSendMessage = {
                val messageToSend = messageText.text.trim()
                if (messageToSend.isNotEmpty()) {
                    val jsonMessage = JSONObject().apply {
                        put("content", messageToSend)
                        put("from", clientUsername)
                        put("role", role)
                        put("to", chatId)
                    }.toString()

                    webSocket?.send(jsonMessage)

                    messages = messages + Message(
                        content = messageToSend,
                        from = clientUsername,
                        time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    )

                    messageText = TextFieldValue("")

                    coroutineScope.launch {
                        listState.animateScrollToItem(messages.size - 1)
                    }
                }
            }
        )
    }
}
