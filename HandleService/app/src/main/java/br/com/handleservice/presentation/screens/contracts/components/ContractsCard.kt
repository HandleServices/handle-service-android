package br.com.handleservice.presentation.screens.contracts.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.handleservice.R
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.presentation.screens.contracts.ContractsViewModel
import br.com.handleservice.presentation.screens.notification.NotificationViewModel
import br.com.handleservice.presentation.screens.settings.SettingsViewModel
import br.com.handleservice.presentation.screens.worker.components.ContractBottomSheet
import br.com.handleservice.ui.preview.ServicesPreviewProvider
import br.com.handleservice.util.FormatUtils.formatBRCurrency
import br.com.handleservice.util.FormatUtils.formatTime
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

// Helper functions to retrieve Worker and Service via API:
@Composable
fun rememberWorker(workerId: Int): br.com.handleservice.domain.model.Worker? {
    val context = LocalContext.current
    val entryPoint = EntryPointAccessors.fromApplication(
        context,
        br.com.handleservice.di.WorkersApiServiceEntryPoint::class.java
    )
    val workersApiService = entryPoint.getWorkersApiService()
    return produceState<br.com.handleservice.domain.model.Worker?>(initialValue = null, key1 = workerId) {
        value = try {
            workersApiService.getWorker(workerId)
        } catch (e: Exception) {
            null
        }
    }.value
}

@Composable
fun rememberService(serviceId: Int, userId: Int): Service? {
    val context = LocalContext.current
    val entryPoint = EntryPointAccessors.fromApplication(
        context,
        br.com.handleservice.di.ServicesApiServiceEntryPoint::class.java
    )
    val servicesApiService = entryPoint.getServicesApiService()
    return produceState<Service?>(initialValue = null, key1 = serviceId) {
        value = try {
            servicesApiService.getService(serviceId, userId)
        } catch (e: Exception) {
            null
        }
    }.value
}

sealed class CreateOrderState {
    object Idle : CreateOrderState()
    object Loading : CreateOrderState()
    data class Success(val data: Any?) : CreateOrderState()
    data class Error(val message: String?) : CreateOrderState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractsCard(
    modifier: Modifier = Modifier,
    order: Order,
    viewModel: ContractsViewModel = hiltViewModel(), // Inject ContractsViewModel
    notificationViewModel: NotificationViewModel,
    settingsViewModel: SettingsViewModel,
    workerId: Int
) {
    var showRatingBottomSheet by remember { mutableStateOf(false) }
    var showFeedbackDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    // Declare a state variable to hold the selected service for contracting
    var selectedService: Service? by remember { mutableStateOf(null) }
    // State to control the bottom sheet visibility
    var showServiceBottomSheet by remember { mutableStateOf(false) }

    val serviceBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val serviceCoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Retrieve Worker and Service from API using helper functions
    val worker = rememberWorker(order.workerId)
    // We'll use the order.workerId as userId; adjust if needed.
    val service = rememberService(order.serviceId, order.workerId)

    if (showRatingBottomSheet) {
        RatingBottomSheet(
            onDismissRequest = { showRatingBottomSheet = false },
            onSubmit = { ratings ->
                showRatingBottomSheet = false
                showFeedbackDialog = true
            }
        )
    }
    if (showFeedbackDialog) {
        FeedbackDialog(onDismiss = { showFeedbackDialog = false })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                when (order.status) {
                    OrderStatus.FINISHED -> 140.dp
                    else -> 126.dp
                }
            )
            .clip(RoundedCornerShape(11.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.padding(start = 14.6.dp)) {
                    Text(
                        text = worker?.businessName ?: "Carregando...",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 10.dp, bottom = 13.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = order.serviceId.toString(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            lineHeight = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .size(13.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                        Text(
                            text = service?.name ?: "Carregando...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight(400),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(start = 7.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp)
                        .padding(end = 14.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(worker?.profilePicUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.profile_image_fallback)
                            .error(R.drawable.profile_image_fallback)
                            .build(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(66.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // If order is finished, show buttons for "Contratar novamente" and "Avaliar"
            if (order.status == OrderStatus.FINISHED) {
                Row {
                    Button(
                        onClick = {
                            // When the user taps "Contratar novamente", set the selected service and open the bottom sheet.
                            selectedService = service // Use the service obtained via API
                            showServiceBottomSheet = true
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(40.dp)
                            .padding(horizontal = 6.dp)
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = if (isLoading) "Aguarde..." else "Contratar novamente",
                            fontSize = 10.sp
                        )
                    }
                    Button(
                        onClick = { showRatingBottomSheet = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(40.dp)
                            .padding(horizontal = 6.dp)
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Avaliar",
                            fontSize = 10.sp
                        )
                    }
                }
            }

            if (order.status == OrderStatus.SCHEDULED) {
                Row(
                    modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp)
                ) {
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.cancelOrder(
                                order.id,
                                onSuccess = {
                                    isLoading = false
                                    Toast.makeText(context, "Pedido cancelado com sucesso!", Toast.LENGTH_SHORT).show()
                                },
                                onError = { errorMessage ->
                                    isLoading = false
                                    Toast.makeText(context, "Erro ao cancelar pedido: $errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            text = if (isLoading) "Cancelando..." else "Cancelar Pedido",
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }

    // Show the ContractBottomSheet when showServiceBottomSheet is true.
    if (showServiceBottomSheet) {
        val serviceBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val serviceCoroutineScope = rememberCoroutineScope()
        ModalBottomSheet(
            onDismissRequest = {
                serviceCoroutineScope.launch {
                    serviceBottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!serviceBottomSheetState.isVisible) {
                        showServiceBottomSheet = false
                    }
                }
            },
            sheetState = serviceBottomSheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.onTertiaryContainer)
                )
            },
            modifier = Modifier.wrapContentSize()
        ) {
            selectedService?.let { service ->
                ContractBottomSheet(
                    service = service,
                    notificationViewModel = notificationViewModel,
                    settingsViewModel = settingsViewModel,
                    workerId = order.workerId
                )
            }
        }
    }
}
