package br.com.handleservice.presentation.screens.worker

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.domain.model.Service
import br.com.handleservice.presentation.screens.favorites.FavoritesViewModel
import br.com.handleservice.presentation.screens.notification.NotificationViewModel
import br.com.handleservice.presentation.screens.address.AddressScreen
import br.com.handleservice.presentation.screens.worker.components.ContractBottomSheet
import br.com.handleservice.presentation.screens.worker.components.ServiceItem
import br.com.handleservice.presentation.screens.worker.components.WorkerCard
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerScreen(
    workerId: Int,
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    viewModel: WorkerViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel,
    notificationViewModel: NotificationViewModel
) {
    LaunchedEffect(workerId) {
        viewModel.loadWorkerById(workerId)
        viewModel.loadServicesForWorker(workerId)
    }

    val worker by viewModel.worker.observeAsState()
    val services by viewModel.services.collectAsState()
    val context = LocalContext.current
    val videoRes = R.raw.encanador

    val selectedService = remember { mutableStateOf<Service?>(null) }

    val serviceBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val serviceCoroutineScope = rememberCoroutineScope()
    var showServiceBottomSheet by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 26.dp)
    ) {
        // Header
        item {
            HandleHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                hasBack = true,
                navController = navController
            )
        }

        // Search bar
        item {
            HandleSearchBar<Service>(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(horizontal = 20.dp),
                placeholder = "Buscar em ${worker?.businessName ?: "..." }"
            )
        }

        // Worker card
        item {
            worker?.let { currentWorker ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 23.dp)
                        .padding(top = 10.dp)
                ) {
                    WorkerCard(
                        modifier = modifier,
                        worker = currentWorker,
                        isFavorite = favoritesViewModel.favorites.value.any { it.name == currentWorker.businessName },
                        onFavoriteClick = { favorite ->
                            if (favoritesViewModel.favorites.value.any { it.name == favorite.name }) {
                                favoritesViewModel.removeFavorite(favorite)
                            } else {
                                favoritesViewModel.addFavorite(favorite)
                            }
                        }
                    )
                    Text(
                        text = "Serviços",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight(500),
                        fontSize = 17.sp,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            } ?: run {
                Text(
                    text = "Carregando trabalhador...",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Services list
        items(services) { service ->
            ServiceItem(
                service = service,
                modifier = Modifier
                    .padding(horizontal = 23.dp, vertical = 2.dp)
                    .clickable {
                        selectedService.value = service
                        showServiceBottomSheet = true
                        serviceCoroutineScope.launch {
                            serviceBottomSheetState.show()
                        }
                    }
            )
        }

        // Video section
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 23.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Vídeo de Apresentação",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight(500),
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                DisposableEffect(Unit) {
                    val exoPlayer = ExoPlayer.Builder(context).build().apply {
                        val videoUri = Uri.parse("file:///raw/encanador.mp4")
                        setMediaItem(androidx.media3.common.MediaItem.fromUri(videoUri))
                        prepare()
                    }
                    onDispose {
                        exoPlayer.release()
                    }
                }

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = ExoPlayer.Builder(ctx).build().apply {
                                val videoUri = Uri.parse("android.resource://${ctx.packageName}/$videoRes")
                                setMediaItem(androidx.media3.common.MediaItem.fromUri(videoUri))
                                prepare()
                            }
                        }
                    }
                )
            }
        }
    }

    if (showServiceBottomSheet) {
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
            containerColor = colorResource(R.color.white),
            tonalElevation = 16.dp,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(colorResource(R.color.handle_gray_tertiary))
                )
            },
            modifier = Modifier
                .wrapContentSize()
        ) {
            selectedService.value?.let { service ->
                ContractBottomSheet(
                    service = service,
                    notificationViewModel = notificationViewModel
                )
            }
        }
    }
}

