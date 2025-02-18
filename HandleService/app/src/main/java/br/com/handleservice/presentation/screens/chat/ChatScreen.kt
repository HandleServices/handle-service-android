package br.com.handleservice.presentation.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.handleservice.presentation.screens.chat.components.ChatContactList
import br.com.handleservice.presentation.screens.chat.components.SkeletonLoader
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
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
    val workerIdToCheck = 3

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
            var hasOrderWithWorker3 = false

            for (i in 0 until ordersArray.length()) {
                val order = ordersArray.getJSONObject(i)
                val workerId = order.getInt("workerId")
                val chatId = order.getInt("channelId")

                workerChannels.add(workerId to chatId)

                if (workerId == workerIdToCheck) {
                    hasOrderWithWorker3 = true
                }
            }

            println("📌 Lista de WorkerIDs e ChatIDs obtidos: $workerChannels")

            if (!hasOrderWithWorker3) {
                println("🚀 Nenhuma Order com Worker ID $workerIdToCheck encontrada. Criando nova Order...")
                createOrderForWorker(client, userId, workerIdToCheck)
            } else {
                println("✅ Order com Worker ID $workerIdToCheck já existe. Nenhuma ação necessária.")
            }

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

fun createOrderForWorker(client: OkHttpClient, userId: String, workerId: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val requestBody = JSONObject().apply {
                put("appointmentDate", "2025-02-17T00:00:00Z")
                put("clientId", userId.toInt())
                put("serviceId", 2)
                put("value", 100.0)
                put("workerId", workerId)
            }

            val request = Request.Builder()
                .url("https://handle-api-1017711936653.us-central1.run.app/api/v1/orders")
                .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody.toString()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                val jsonResponse = JSONObject(responseBody)
                val newChatId = jsonResponse.getInt("channelId")

                println("✅ Nova Order criada com sucesso! WorkerID: $workerId | ChatID: $newChatId")
            } else {
                println("❌ Erro ao criar a Order: ${response.message}")
            }
        } catch (e: Exception) {
            println("❌ Exceção ao criar Order: ${e.message}")
        }
    }
}
