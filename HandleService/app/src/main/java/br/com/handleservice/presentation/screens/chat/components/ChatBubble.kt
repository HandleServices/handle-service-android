package br.com.handleservice.presentation.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.presentation.screens.chat.model.Message
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun ChatBubble(message: Message, currentUserRole: String) {
    val isSentByUser = message.from != "worker"

    val formattedTime = try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val targetFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }

        val date = originalFormat.parse(message.time)
        date?.let { targetFormat.format(it) } ?: message.time
    } catch (e: Exception) {
        message.time
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 38.dp),
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isSentByUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = message.content,
                    fontSize = 16.sp,
                    color = if (isSentByUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formattedTime,
                    fontSize = 12.sp,
                    color = if (isSentByUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}
