package br.com.handleservice.presentation.screens.worker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.domain.model.Worker
import br.com.handleservice.ui.preview.WorkerPreviewProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun WorkerCard(
    modifier: Modifier = Modifier,
    worker: Worker ?= null
) {
    if (worker != null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(top = 38.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(133.dp)
                ,
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation =  10.dp,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    // TO-DO: Implementar "Disponível Agora"
                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = worker.businessName,
                        color = colorResource(R.color.handle_titles),
                        fontWeight = FontWeight(500),
                        fontSize = 17.sp,
                    )

                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "${worker.firstName} ${worker.lastName}",
                            color = colorResource(R.color.handle_titles),
                            fontWeight = FontWeight(300),
                            fontSize = 13.sp,
                        )
                        Text(
                            text = " • ${worker.job}",
                            color = colorResource(R.color.handle_gray),
                            fontWeight = FontWeight(400),
                            fontSize = 11.sp,
                        )
                    }
                    HorizontalDivider(
                        color = colorResource(R.color.handle_gray_secondary),
                        thickness = 0.6.dp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "Star",
                            tint = colorResource(R.color.handle_blue),
                            modifier = Modifier
                                .size(9.3.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        // TO-DO: Implementar lógica de avaliações
                        Text(
                            text = "4,8",
                            fontSize = 13.sp,
                            lineHeight = 13.sp,
                            color = colorResource(R.color.handle_blue)
                        )
                        Text(
                            text = " • (170 avaliações)",
                            color = colorResource(R.color.handle_gray),
                            fontWeight = FontWeight(400),
                            fontSize = 11.sp,
                            lineHeight = 13.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = colorResource(R.color.handle_blue),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (-34).dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(worker.profilePicUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.profile_image_fallback)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape)
                )

            }
        }
    }
}


@Composable
@Preview
private fun WorkerCardPreview () {
    WorkerCard(
        worker = WorkerPreviewProvider().values.first()
    )
}

