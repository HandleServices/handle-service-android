package br.com.handleservice.presentation.screens.contracts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.domain.model.Order
import br.com.handleservice.domain.model.OrderStatus
import br.com.handleservice.ui.preview.OrderPreviewProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ContractsCard (
    modifier: Modifier = Modifier,
    order: Order
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(if (order.status == OrderStatus.FINISHED) 140.dp else 126.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(Color.White)
    ) {
        Column (
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Row (
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column (
                    modifier = Modifier
                        .padding(start = 14.6.dp),
                ) {
                    Text(
                        text = order.worker.businessName,
                        color = colorResource(R.color.handle_titles),
                        fontWeight = FontWeight(500),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 13.dp)

                    )
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "85",
                            color = colorResource(R.color.handle_gray),
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            lineHeight = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .size(13.dp)
                                .background(
                                    color = colorResource(R.color.handle_background_gray),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                        Text(
                            text = order.service.name,
                            color = colorResource(R.color.handle_gray),
                            fontWeight = FontWeight(400),
                            fontSize = 11.sp,
                            modifier = Modifier
                                .padding(start = 7.dp)
                        )
                    }
                }
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp)
                        .padding(end = 14.dp),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(order.worker.profilePicUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.profile_image_fallback)
                            .build(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(66.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            if(order.status == OrderStatus.FINISHED) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 13.dp),
                    shape = RoundedCornerShape(10),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.handle_blue)
                    )
                ) {
                    Text(
                        text = "Contratar novamente",
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

// TO-DO: implements that after mobile delivery
@Composable
fun LearnMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Saiba mais",
            color = colorResource(id = R.color.handle_blue),
            modifier = Modifier.padding(end = 8.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "Arrow",
            tint = colorResource(id = R.color.handle_blue),
        )
    }
}


@Composable
@Preview
private fun ContractsCardPreview () {
    ContractsCard(
        order = OrderPreviewProvider().values.first(),
    )
}