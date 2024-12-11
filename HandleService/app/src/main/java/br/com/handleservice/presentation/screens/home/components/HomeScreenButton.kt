package br.com.handleservice.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.handleservice.R
import br.com.handleservice.presentation.screens.home.JobsCategoriesType

@Composable
fun HomeScreenButton(category: JobsCategoriesType, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(167.dp)
            .height(64.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCEE1F2))
    ) {
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight()
                .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            Text(
                text = category.name,
                color = colorResource(R.color.handle_blue),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        ) {
            Icon(
                painter = painterResource(id = category.iconResId),
                contentDescription = category.name,
                tint = colorResource(R.color.handle_blue),
                modifier = Modifier
                    .size(44.dp)
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 6.dp),
            )
        }
    }
}