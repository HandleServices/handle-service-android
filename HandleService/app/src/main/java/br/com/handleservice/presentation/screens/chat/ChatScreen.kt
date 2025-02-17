package br.com.handleservice.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.handleservice.presentation.screens.chat.components.ChatContactList
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

    if (isLoading) {
        SkeletonLoader()
    } else {
        ChatContactList(contacts = chatContacts, navController = navController)
    }
}

// 🔹 **Skeleton Loader enquanto carrega os contatos**
@Composable
fun SkeletonLoader() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        repeat(6) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.3f))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.5f)
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(0.3f)
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                }
            }
        }
    }
}

// 🔹 **Função para buscar os contatos**
fun fetchChatContacts(chatContacts: MutableList<ChatContact>, onComplete: () -> Unit) {
    val client = OkHttpClient()
    val userId = "1"

    CoroutineScope(Dispatchers.IO).launch {
        try {
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

            workerChannels.forEach { (workerId, chatId) ->
                val workerRequest = Request.Builder()
                    .url("https://handle-api-1017711936653.us-central1.run.app/api/v1/workers/$workerId")
                    .build()

                val workerResponse = client.newCall(workerRequest).execute()
                val workerJson = workerResponse.body?.string() ?: return@launch
                val workerData = JSONObject(workerJson)

                val workerName = workerData.getString("businessName")
                val profilePicUrl = workerData.getString("profilePicUrl")

                withContext(Dispatchers.Main) {
                    chatContacts.add(
                        ChatContact(chatId = chatId, name = workerName, imageUrl = profilePicUrl)
                    )
                }
            }
        } catch (e: Exception) {
            println("❌ Erro ao buscar contatos: ${e.message}")
        } finally {
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }
}