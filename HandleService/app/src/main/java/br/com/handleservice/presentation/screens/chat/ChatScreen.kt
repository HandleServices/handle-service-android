package br.com.handleservice.presentation.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.handleservice.presentation.screens.chat.components.ChatContactList
import br.com.handleservice.presentation.screens.chat.components.SkeletonLoader
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

data class ChatContact(val chatId: Int, val name: String, val imageUrl: String)

@Composable
fun ChatScreen(navController: NavController) {
    val chatContacts = remember { mutableStateListOf<ChatContact>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        fetchChatContacts(chatContacts) {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading && chatContacts.isEmpty()) {
            SkeletonLoader()
        } else {
            ChatContactList(contacts = chatContacts, navController = navController)
        }
    }
}

fun fetchChatContacts(chatContacts: MutableList<ChatContact>, onComplete: () -> Unit) {
    val client = OkHttpClient()
    val userId = "1"

    CoroutineScope(Dispatchers.IO).launch {
        try {
            println("🔍 Buscando ORDERS do usuário ID: $userId")
            val ordersRequest = Request.Builder()
                .url("https://handle-api-1017711936653.us-central1.run.app/api/v1/orders/all/$userId")
                .build()

            val ordersResponse = client.newCall(ordersRequest).execute()
            val ordersJson = ordersResponse.body?.string() ?: return@launch
            val ordersArray = JSONArray(ordersJson)

            val workerChannels = mutableListOf<Pair<Int, Int>>()

            for (i in 0 until ordersArray.length()) {
                val order = ordersArray.getJSONObject(i)
                val workerId = order.getInt("workerId")
                val chatId = order.getInt("channelId")
                workerChannels.add(workerId to chatId)
            }

            println("📌 Lista de WorkerIDs e ChatIDs obtidos: $workerChannels")

            workerChannels.forEach { (workerId, chatId) ->
                println("🔍 Buscando dados do Worker ID: $workerId")
                val workerRequest = Request.Builder()
                    .url("https://handle-api-1017711936653.us-central1.run.app/api/v1/workers/$workerId")
                    .build()

                val workerResponse = client.newCall(workerRequest).execute()
                val workerJson = workerResponse.body?.string() ?: return@launch
                val workerData = JSONObject(workerJson)

                val workerName = workerData.getString("businessName")
                val profilePicUrl = workerData.getString("profilePicUrl")

                println("✅ Worker Processado: ID = $workerId | Nome = $workerName | Imagem = $profilePicUrl")

                withContext(Dispatchers.Main) {
                    chatContacts.add(
                        ChatContact(chatId = chatId, name = workerName, imageUrl = profilePicUrl)
                    )

                    if (chatContacts.size == 1) {
                        onComplete()
                    }
                }
            }
        } catch (e: Exception) {
            println("❌ Erro ao buscar contatos: ${e.message}")
        }
    }
}
