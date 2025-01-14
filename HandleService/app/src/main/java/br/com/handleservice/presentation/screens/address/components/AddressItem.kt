package br.com.handleservice.presentation.screens.address.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.domain.model.Address
import br.com.handleservice.ui.preview.AddressPreviewProvider

@Composable
fun AddressItem(
    address: Address,
    modifier: Modifier = Modifier,
    onClick: (Address) -> Unit,
    onMoreVertClick: (Address) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (address.isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(13.dp)
                    )
                } else Modifier
            )
            .clickable { onClick(address) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = address.type,
                        fontWeight = FontWeight(500),
                        fontSize = 16.sp,
                        color = if (address.isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (address.isSelected) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(end = 4.dp)
                            )
                        }
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "Menu",
                            tint = if (address.isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onMoreVertClick(address) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    modifier = Modifier.padding(0.dp),
                    text = "${address.street}, ${address.number} - ${address.neighborhood}",
                    fontWeight = FontWeight(370),
                    fontSize = 16.sp,
                    color = if (address.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                Text(
                    modifier = Modifier.padding(0.dp),
                    text = "${address.city}/${address.state}",
                    fontWeight = FontWeight(300),
                    fontSize = 13.sp,
                    color = if (address.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                address.note?.let {
                    Text(
                        modifier = Modifier.padding(0.dp),
                        text = it,
                        fontWeight = FontWeight(400),
                        fontSize = 11.sp,
                        color = if (address.isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    )
}

@Composable
@Preview
private fun AddressItemPreview () {
    AddressItem(
        address = AddressPreviewProvider().values.elementAt(1),
        onClick = {},
        onMoreVertClick = {}
    )
}