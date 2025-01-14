package br.com.handleservice.ui.components.handleHeader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.handleservice.R
import br.com.handleservice.presentation.navigation.Route
import br.com.handleservice.presentation.screens.address.AddressViewModel
import br.com.handleservice.presentation.shared.SharedAddressViewModel

@Preview
@Composable
fun HandleHeader(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    hasBack: Boolean = false,
    sharedViewModel: AddressViewModel = hiltViewModel()
) {
    val selectedAddress by sharedViewModel.selectedAddress.collectAsState()
    val colorScheme = MaterialTheme.colorScheme // Adicionado para acessar o esquema de cores

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colorScheme.background), // Alterado para MaterialTheme
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (hasBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Voltar",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController?.popBackStack() },
                    tint = colorScheme.primary // Alterado para MaterialTheme
                )
            } else {
                Spacer(modifier = Modifier.width(24.dp))
            }

            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navController?.navigate(Route.Address.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary), // Alterado para MaterialTheme
                    shape = RoundedCornerShape(10.dp),
                    border = null,
                    modifier = Modifier
                        .width(200.dp)
                        .height(23.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                    ),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "R. Franco Costa, 78",
                            modifier = Modifier.align(Alignment.Center),
                            color = colorScheme.onPrimary, // Alterado para MaterialTheme
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontSize = 12.sp)
                        )
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Dropdown",
                            tint = colorScheme.onPrimary, // Alterado para MaterialTheme
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                                .size(20.dp)
                        )
                    }
                }
            }

            IconButton(
                onClick = { println("Notification icon clicked!") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notification icon",
                    tint = colorScheme.primary, // Alterado para MaterialTheme
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}



