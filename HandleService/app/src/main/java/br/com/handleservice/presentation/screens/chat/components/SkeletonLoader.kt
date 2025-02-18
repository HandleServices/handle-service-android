package br.com.handleservice.presentation.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonLoader() {
    Column {
        repeat(5) { // 🔹 Mostra 5 Skeletons enquanto carrega
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Gray.copy(alpha = 0.3f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(0.3f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                }
            }
        }
    }
}
