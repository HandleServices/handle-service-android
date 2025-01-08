package br.com.handleservice.presentation.screens.address.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.domain.model.Address

@Composable
fun BottomSheetLayout (
    address: Address? = null,
    onCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    if (address != null) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = address.type,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600
                ),
                color = colorResource(R.color.black),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
                thickness = 1.dp,
                color = colorResource(R.color.handle_gray_secondary)
            )
            Spacer(modifier = Modifier.height(20.dp))

            content()

            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = onCancel
            ) {
                Text(
                    text = "Cancelar",
                    color = colorResource(R.color.handle_blue),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}