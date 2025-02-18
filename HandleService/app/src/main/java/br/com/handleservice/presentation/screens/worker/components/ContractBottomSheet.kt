package br.com.handleservice.presentation.screens.worker.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.handleservice.R
import br.com.handleservice.data.model.OrderCreateDTO
import br.com.handleservice.di.CreateOrderEntryPoint
import br.com.handleservice.domain.model.Service
import br.com.handleservice.domain.usecases.orders.CreateOrderUseCase
import br.com.handleservice.presentation.screens.notification.Notification
import br.com.handleservice.presentation.screens.notification.NotificationViewModel
import br.com.handleservice.presentation.screens.settings.SettingsViewModel
import br.com.handleservice.ui.components.loading.UiState
import br.com.handleservice.ui.preview.ServicesPreviewProvider
import br.com.handleservice.util.FormatUtils.formatBRCurrency
import br.com.handleservice.util.FormatUtils.formatTime
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed class CreateOrderState {
    object Idle : CreateOrderState()
    object Loading : CreateOrderState()
    data class Success(val data: Any?) : CreateOrderState()
    data class Error(val message: String?) : CreateOrderState()
}

@Composable
fun ContractBottomSheet(
    service: Service? = null,
    notificationViewModel: NotificationViewModel,
    settingsViewModel: SettingsViewModel,
    workerId: Int
) {
    val context = LocalContext.current

    val entryPoint = EntryPointAccessors.fromApplication(
        context,
        CreateOrderEntryPoint::class.java
    )
    val createOrderUseCase: CreateOrderUseCase = entryPoint.createOrderUseCase()

    var createOrderState by remember { mutableStateOf<CreateOrderState>(CreateOrderState.Idle) }
    val coroutineScope = rememberCoroutineScope()

    val dateFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf(today) }
    val dates = (0 until 14).map { today.plusDays(it.toLong()) }

    val times = generateSequence(LocalTime.of(7, 0)) { it.plusMinutes(30) }
        .takeWhile { it.isBefore(LocalTime.of(23, 30)) }
        .toList()
    var selectedTime by remember { mutableStateOf(times.first()) }

    fun handleContract(
        serviceName: String,
        selectedDate: String,
        selectedTime: String
    ) {
        // This function sends the notification (and also adds it to the view model)
        val notificationsEnabled = settingsViewModel.notificationsEnabled.value
        if (!notificationsEnabled) {
            Toast.makeText(context, "Notificações estão desativadas.", Toast.LENGTH_SHORT).show()
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!permissionGranted) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
                return
            }
        }
        val notificationTitle = "Serviço Contratado"
        val notificationMessage = "$serviceName foi contratado para $selectedDate às $selectedTime."

        NotificationUtils.showNotification(
            context = context,
            title = notificationTitle,
            message = notificationMessage
        )
        notificationViewModel.addNotification(
            Notification(
                title = notificationTitle,
                description = notificationMessage,
                time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        service?.let { svc ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = svc.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 21.sp
                )
                Text(
                    text = " • ${service.estimatedTime?.formatTime() ?: "00:00"}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = svc.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Escolha o dia e horário",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = selectedDate.format(dateFormatter),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(dates) { date ->
                    DateButton(
                        date = date,
                        onSelectDate = { selectedDate = it },
                        isSelected = selectedDate == date
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                items(times) { time ->
                    TimeButton(
                        time = time,
                        onSelectTime = { selectedTime = it },
                        isSelected = selectedTime == time
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val localDateTime = LocalDateTime.of(selectedDate, selectedTime)
                    val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
                    val appointmentDateIso = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                    val orderCreateDTO = OrderCreateDTO(
                        appointmentDate = appointmentDateIso,
                        value = svc.value,
                        serviceId = svc.id,
                        workerId = workerId,
                        clientId = 1
                    )


                    Log.d("CreateOrder", "Enviando OrderCreateDTO: $orderCreateDTO")

                    coroutineScope.launch {
                        createOrderUseCase(orderCreateDTO).collect { uiState ->
                            createOrderState = when (uiState) {
                                is UiState.Loading -> CreateOrderState.Loading
                                is UiState.Success -> CreateOrderState.Success(uiState.data)
                                is UiState.Error -> CreateOrderState.Error(uiState.message)
                                else -> CreateOrderState.Idle
                            }
                        }
                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 13.dp),
                    text = "Contratar Serviço",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.weight(0.6f))
                Text(
                    modifier = Modifier.padding(horizontal = 13.dp),
                    text = formatBRCurrency(svc.value),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
            }
            when (createOrderState) {
                is CreateOrderState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp), // ajuste a altura conforme necessário
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is CreateOrderState.Success -> {
                    LaunchedEffect(Unit) {
                        handleContract(
                            serviceName = svc.name,
                            selectedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            selectedTime = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                        )
                        Toast.makeText(context, "Pedido criado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                }
                is CreateOrderState.Error -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "Erro: ${(createOrderState as CreateOrderState.Error).message}", Toast.LENGTH_LONG).show()
                    }
                }
                else -> { /* Idle state: do nothing */ }
            }
        }
    }
}

@Composable
fun DateButton(
    date: LocalDate,
    isSelected: Boolean,
    onSelectDate: (LocalDate) -> Unit
) {
    val customDayAbbreviations = mapOf(
        DayOfWeek.MONDAY to "seg.",
        DayOfWeek.TUESDAY to "ter.",
        DayOfWeek.WEDNESDAY to "qua.",
        DayOfWeek.THURSDAY to "qui.",
        DayOfWeek.FRIDAY to "sex.",
        DayOfWeek.SATURDAY to "sab.",
        DayOfWeek.SUNDAY to "dom."
    )

    val dayAbbreviation = customDayAbbreviations[date.dayOfWeek] ?: ""
    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM"))

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(58.dp)
            .width(80.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                else MaterialTheme.colorScheme.onTertiaryContainer
            )
            .clickable { onSelectDate(date) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = dayAbbreviation,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight(300)
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary // Alterado para MaterialTheme
                else MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
        }
    }
}

@Composable
fun TimeButton(
    time: LocalTime,
    onSelectTime: (LocalTime) -> Unit,
    isSelected: Boolean
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onTertiaryContainer
    val textColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(29.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .background(
                backgroundColor
            )
            .clickable {
                onSelectTime(
                    time
                )
            }
    ) {
        Text(
            text = time.format(DateTimeFormatter.ofPattern("HH:mm'h'")),
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight(300),
            color = textColor,
        )
    }
}


@Composable
@Preview
private fun ContractBottomSheetPreview() {
    ContractBottomSheet(
        service = ServicesPreviewProvider().values.first(),
        notificationViewModel = TODO(),
        settingsViewModel = TODO(),
        workerId = TODO()
    )
}
