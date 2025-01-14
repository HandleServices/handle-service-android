package br.com.handleservice.presentation.screens.worker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.domain.model.Service
import br.com.handleservice.ui.preview.ServicesPreviewProvider
import br.com.handleservice.util.FormatUtils.formatBRCurrency
import br.com.handleservice.util.FormatUtils.formatTime

@Preview
@Composable
fun ServiceItem(
    modifier: Modifier = Modifier,
    service: Service? = null
) {
    if (service != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        text = service.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight(500),
                        fontSize = 15.sp,
                    )
                    Text(
                        text = " • ${service.estimatedTime.formatTime()}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight(400),
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(horizontal = 5.dp),
                    text = service.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight(400),
                    fontSize = 13.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 5.dp),
                    text = formatBRCurrency(service.value),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight(400),
                    fontSize = 15.sp,
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
@Preview
private fun ServiceItemPreview () {
    ServiceItem(
        service = ServicesPreviewProvider().values.first()
    )
}