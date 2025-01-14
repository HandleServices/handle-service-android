package br.com.handleservice.presentation.screens.contracts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.domain.model.Service
import br.com.handleservice.presentation.screens.address.AddressScreen
import br.com.handleservice.presentation.screens.contracts.components.ContractsCard
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar

@Preview
@Composable
fun ContractsScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: ContractsViewModel = hiltViewModel()
) {
    val groupedContracts by viewModel.groupedContracts.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 26.dp),
        horizontalAlignment = Alignment.Start
    ) {

        HandleHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
                .padding(bottom = 26.dp),
            navController = navController
        )

        HandleSearchBar<Service>(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 15.dp),
            placeholder = "Buscar no histórico"
        )

        Text(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 1.dp)
                .padding(horizontal = 26.dp),
            text = stringResource(R.string.agendados),
            fontWeight = FontWeight(500),
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyColumn(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 26.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedContracts.scheduled.forEach { (date, contracts) ->
                item {
                    Text(
                        text = date,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 1.dp)
                    )
                }
                items(contracts) { contract ->
                    ContractsCard(modifier, contract)
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 1.dp)
                .padding(horizontal = 26.dp),
            text = stringResource(R.string.historico),
            fontWeight = FontWeight(500),
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedContracts.finished.forEach { (date, contracts) ->
                item {
                    Text(
                        text = date,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 1.dp)
                    )
                }
                items(contracts) { contract ->
                    ContractsCard(modifier, contract)
                }
            }
        }
    }
}
