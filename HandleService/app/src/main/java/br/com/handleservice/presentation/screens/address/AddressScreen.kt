package br.com.handleservice.presentation.screens.address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.domain.model.Address
import br.com.handleservice.presentation.screens.address.components.AddressItem
import br.com.handleservice.presentation.screens.address.components.BottomSheetLayout
import br.com.handleservice.presentation.screens.address.components.EditAddressForm
import br.com.handleservice.presentation.screens.worker.components.ContractBottomSheet
import kotlinx.coroutines.launch

sealed class BottomSheetContentState {
    data object Actions : BottomSheetContentState()
    data object EditForm : BottomSheetContentState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddressScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val addresses by viewModel.addresses.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var selectedAddress by remember { mutableStateOf<Address?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetContentState by remember { mutableStateOf<BottomSheetContentState>(BottomSheetContentState.Actions) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.background))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(101.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Voltar",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController?.popBackStack()
                    },
                tint = colorResource(R.color.handle_blue)
            )

            Text(
                text = "Meus endereços",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600
                ),
                color = colorResource(R.color.black),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 24.dp),
                textAlign = TextAlign.Center
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            thickness = 1.dp,
            color = colorResource(R.color.handle_gray_secondary)
        )
        Spacer(modifier = Modifier.height(17.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
        ) {
            items(addresses) { address ->
                AddressItem (
                    address = address,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    onClick = { clickedAddress ->
                        viewModel.toggleAddressSelection(clickedAddress)
                    },
                    onMoreVertClick = { clickedAddress ->
                        selectedAddress = clickedAddress
                        showBottomSheet = true
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }
                )
            }
        }
    }

    if (showBottomSheet && selectedAddress != null) {
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
                .wrapContentSize(),
        ) {
            BottomSheetLayout(
                address = selectedAddress,
                onCancel = {
                    showBottomSheet = false
                    bottomSheetContentState = BottomSheetContentState.Actions
                }
            ) {
                when (bottomSheetContentState) {
                    is BottomSheetContentState.Actions -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { showBottomSheet = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.handle_blue),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                            ) {
                                Text(text = "Excluir", fontSize = 16.sp)
                            }

                            Button(
                                onClick = { bottomSheetContentState = BottomSheetContentState.EditForm },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.handle_blue),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                            ) {
                                Text(text = "Editar", fontSize = 16.sp)
                            }
                        }
                    }
                    is BottomSheetContentState.EditForm -> {
                        EditAddressForm(
                            address = selectedAddress!!,
                            onSave = {
                                showBottomSheet = false
                                bottomSheetContentState = BottomSheetContentState.Actions
                            }
                        )
                    }
                }
            }
        }
    }
}
