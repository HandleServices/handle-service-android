package br.com.handleservice.presentation.screens.contracts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var providerRating by remember { mutableStateOf(0) }
    var serviceRating by remember { mutableStateOf(0) }
    var handleRating by remember { mutableStateOf(0) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Avalie sua experiência",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )

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

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    onSubmit(
                        mapOf(
                            "providerRating" to providerRating,
                            "serviceRating" to serviceRating,
                            "handleRating" to handleRating
                        )
                    )
                    scope.launch { sheetState.hide() }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10),
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
        }
    }
}

@Composable
fun HorizontalDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    )
}
