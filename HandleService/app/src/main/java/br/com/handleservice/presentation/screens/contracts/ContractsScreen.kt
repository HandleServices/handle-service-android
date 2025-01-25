import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.domain.model.Service
import br.com.handleservice.presentation.screens.contracts.ContractsViewModel
import br.com.handleservice.presentation.screens.contracts.components.ContractsCard
import br.com.handleservice.ui.components.handleHeader.HandleHeader
import br.com.handleservice.ui.components.searchbar.HandleSearchBar

@Composable
fun ContractsScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: ContractsViewModel = hiltViewModel()
) {
    val groupedContracts by viewModel.groupedContracts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = true)

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isLoading) {
                items(5) {
                    SkeletonCard()
                }
            } else {
                item {
                    Text(
                        text = stringResource(R.string.agendados),
                        fontWeight = FontWeight(500),
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(top = 30.dp, bottom = 1.dp)
                    )
                }

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
                        ContractsCard(modifier = Modifier.fillMaxWidth(), order = contract)
                    }
                }

                item {
                    Text(
                        text = stringResource(R.string.historico),
                        fontWeight = FontWeight(500),
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 30.dp, bottom = 1.dp)
                    )
                }

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
                        ContractsCard(modifier = Modifier.fillMaxWidth(), order = contract)
                    }
                }
            }
        }
    }
}

@Composable
fun SkeletonCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
    )
}
