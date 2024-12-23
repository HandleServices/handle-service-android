package br.com.handleservice.presentation.screens.worker

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
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.domain.model.Service
import br.com.handleservice.presentation.screens.worker.components.ContractBottomSheet
import br.com.handleservice.presentation.screens.worker.components.ServiceItem
import br.com.handleservice.presentation.screens.worker.components.WorkerCard
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WorkerScreen(
    query: String? = null,
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    viewModel: WorkerViewModel = hiltViewModel()
) {
    val worker by viewModel.worker.observeAsState()
    val services by viewModel.services.collectAsState()
    val context = LocalContext.current
    val videoRes = R.raw.encanador

    val selectedService = remember { mutableStateOf<Service?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
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
                placeholder = "Buscar em ${worker?.businessName}"
            )
        }

        // Worker card
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 23.dp)
                    .padding(top = 10.dp)
            ) {
                WorkerCard(
                    modifier = modifier,
                    worker = worker
                )
                Text(
                    text = "Serviços",
                    color = colorResource(R.color.handle_titles),
                    fontWeight = FontWeight(500),
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier.height(15.dp))
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
                        showBottomSheet = true
                        coroutineScope.launch {
                            bottomSheetState.show()
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
                    color = colorResource(R.color.handle_titles),
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

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            },
            sheetState = bottomSheetState,
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
                ContractBottomSheet(service = service)
            }
        }
    }
}


