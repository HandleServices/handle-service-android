package br.com.handleservice.presentation.screens.contracts.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeedbackDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Ícone de confirmação",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(55.dp)
                    .rotate(21.57f) // Gira o ícone levemente
            )
        },
        title = {
            Text(
                text = "Obrigado pelo feedback",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = "Isso ajudará muito outros usuários",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                modifier = Modifier.width(190.dp)
            )
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    )
}

@Preview(showBackground = true)
@Composable
fun FeedbackDialogPreview() {
    MaterialTheme {
        FeedbackDialog(
            onDismiss = { /* Simula a ação de fechar no Preview */ }
        )
    }
}
