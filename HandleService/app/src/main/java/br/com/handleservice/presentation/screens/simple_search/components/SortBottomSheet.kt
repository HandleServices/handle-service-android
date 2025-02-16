package br.com.handleservice.presentation.screens.simple_search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    onDismissRequest: () -> Unit,
    onApplySort: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var selectedSort by remember { mutableStateOf("default") }

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
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ordenar por",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    "default" to Pair("Ordenação\nPadrão", R.drawable.compare_arrows),
                    "price" to Pair("Preço\n", R.drawable.coin),
                    "rating" to Pair("Avaliação\n", null) // Usamos o ícone do Material Design para avaliação
                ).forEach { (type, pair) ->
                    val (label, drawableRes) = pair
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(80.dp) // Garante que os textos fiquem alinhados uniformemente
                    ) {
                        IconButton(
                            onClick = { selectedSort = type },
                            modifier = Modifier.size(60.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (selectedSort == type) MaterialTheme.colorScheme.primary else Color.LightGray
                            )
                        ) {
                            if (drawableRes != null) {
                                Image(
                                    painter = painterResource(id = drawableRes),
                                    contentDescription = label,
                                    modifier = Modifier.size(30.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = label,
                                    tint = if (selectedSort == type) MaterialTheme.colorScheme.onPrimary else Color.Gray,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = label,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
