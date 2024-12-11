package br.com.handleservice.presentation.screens.contracts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.handleservice.R
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.domain.model.Service
import br.com.handleservice.presentation.screens.contracts.components.ContractsCard
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar

@Preview
@Composable
fun ContractsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContractsViewModel = hiltViewModel()
) {
    val groupedContracts by viewModel.groupedContracts.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(vertical = 26.dp),
        horizontalAlignment = Alignment.Start
    ) {

        HandleHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
                .padding(bottom = 26.dp)
        )

        HandleSearchBar<Service>(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 15.dp)
            ,
            placeholder = "Buscar no histórico"
        )

        Text(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 1.dp)
                .padding(horizontal = 26.dp),
            text = stringResource(R.string.agendados),
            fontWeight = FontWeight(500),
            fontSize = 19.sp
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
                        color = colorResource(R.color.handle_titles),
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
            fontSize = 19.sp
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
                        color = colorResource(R.color.handle_titles),
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
