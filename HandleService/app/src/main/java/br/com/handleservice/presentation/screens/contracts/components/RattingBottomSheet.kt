package br.com.handleservice.presentation.screens.contracts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingBottomSheet(
    onDismissRequest: () -> Unit,
    onSubmit: (ratings: Map<String, Int>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var providerRating by remember { mutableIntStateOf(0) }
    var serviceRating by remember { mutableIntStateOf(0) }
    var handleRating by remember { mutableIntStateOf(0) }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
            onDismissRequest()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Avalie sua experiência",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            RatingSection(
                title = "O prestador",
                selectedRating = providerRating,
                onRatingSelected = { providerRating = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            RatingSection(
                title = "O serviço prestado",
                selectedRating = serviceRating,
                onRatingSelected = { serviceRating = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            RatingSection(
                title = "A handle",
                selectedRating = handleRating,
                onRatingSelected = { handleRating = it }
            )

            Spacer(modifier = Modifier.height(43.dp))

            Button(
                onClick = {
                    onSubmit(
                        mapOf(
                            "providerRating" to providerRating,
                            "serviceRating" to serviceRating,
                            "handleRating" to handleRating
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Avaliar",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
