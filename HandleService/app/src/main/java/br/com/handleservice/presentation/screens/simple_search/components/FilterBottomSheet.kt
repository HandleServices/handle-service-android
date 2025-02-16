package br.com.handleservice.presentation.screens.simple_search.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismissRequest: () -> Unit,
    onApplyFilters: (String, String, Int?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var startDate by remember { mutableStateOf(dateFormat.format(Calendar.getInstance().time)) }
    var endDate by remember { mutableStateOf(dateFormat.format(Calendar.getInstance().time)) }
    var selectedRating by remember { mutableStateOf<Int?>(null) }

    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                onDateSelected(dateFormat.format(selectedCalendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Filtrar por",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Disponibilidade (Datas)
            Text(text = "Disponibilidade", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { showDatePicker(context) { startDate = it } },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f).height(45.dp)
                ) {
                    Text(startDate, color = Color.White)
                }
                Text("➝", color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(horizontal = 8.dp))
                Button(
                    onClick = { showDatePicker(context) { endDate = it } },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f).height(45.dp)
                ) {
                    Text(endDate, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Avaliação
            Text(text = "Avaliação", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 1..5) {
                    Button(
                        onClick = { selectedRating = i },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedRating == i) MaterialTheme.colorScheme.primary else Color.LightGray
                        ),
                        modifier = Modifier.size(70.dp, 40.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$i",
                                color = if (selectedRating == i) MaterialTheme.colorScheme.onPrimary else Color.Gray,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                modifier = Modifier.size(36.dp),
                                imageVector = Icons.Filled.Star,
                                contentDescription = "$i estrelas",
                                tint = if (selectedRating == i) MaterialTheme.colorScheme.onPrimary else Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onApplyFilters(startDate, endDate, selectedRating) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f).height(60.dp)
                ) {
                    Text("Aplicar Filtros")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        startDate = dateFormat.format(Calendar.getInstance().time)
                        endDate = dateFormat.format(Calendar.getInstance().time)
                        selectedRating = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.weight(1f).height(60.dp)
                ) {
                    Text("Limpar Filtros")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
