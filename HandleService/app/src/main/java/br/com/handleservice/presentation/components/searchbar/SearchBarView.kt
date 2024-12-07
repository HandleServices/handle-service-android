package br.com.handleservice.presentation.components.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.Brush

enum class Side {
    Top, Bottom, Start, End
}

@Composable
fun SideShadow(
    color: Color = Color.Black,
    alpha: Float = 0.1f,
    shadowSize: Dp = 4.dp,
    sides: List<Side> = listOf(Side.Bottom),
    content: @Composable () -> Unit
) {
    Box {
        content()
        sides.forEach { side ->
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .then(
                        when (side) {
                            Side.Top -> Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(shadowSize)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            color.copy(alpha = alpha),
                                            Color.Transparent
                                        )
                                    )
                                )
                            Side.Bottom -> Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(shadowSize)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            color.copy(alpha = alpha)
                                        )
                                    )
                                )
                            Side.Start -> Modifier
                                .align(Alignment.CenterStart)
                                .width(shadowSize)
                                .fillMaxHeight()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            color.copy(alpha = alpha),
                                            Color.Transparent
                                        )
                                    )
                                )
                            Side.End -> Modifier
                                .align(Alignment.CenterEnd)
                                .width(shadowSize)
                                .fillMaxHeight()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            color.copy(alpha = alpha)
                                        )
                                    )
                                )
                        }
                    )
            )
        }
    }
}

@Composable
fun <T : SearchableClass> HandleSearchBar(
    searchableElements: List<T>,
    placeholder: @Composable () -> Unit,
    icon: ImageVector? = Icons.Default.Search,
    iconColor: Color = Color.Black,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 0.dp,
    elevation: Dp = 4.dp,
    shadowColor: Color = Color.Black.copy(alpha = 0.25f) // Adjust alpha as needed
) {
    val viewModel: SearchBarViewModel<T> = viewModel()
    viewModel.setSearchableElements(searchableElements)

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val active = isSearching || searchText.isNotEmpty()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = shadowColor,
                spotColor = shadowColor,
                clip = false
            )
            .background(Color.White, shape = RoundedCornerShape(cornerRadius))
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    if (icon != null) {
                        Icon(
                            icon,
                            tint = iconColor,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (searchText.isEmpty()) {
                            placeholder()
                        }
                        innerTextField()
                    }
                    if (active) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { viewModel.onSearchTextChange("") },
                            tint = iconColor
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

data class Person(
    val firstName: String,
    val lastName: String
) : SearchableClass {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    val persons = listOf(
        Person(firstName = "Philipp", lastName = "Lackner"),
        Person(firstName = "Beff", lastName = "Jezos"),
        Person(firstName = "Chris P.", lastName = "Bacon"),
        Person(firstName = "Jeve", lastName = "Stops")
    )

    // Preview the search bar with some example data
    HandleSearchBar<Person>(
        searchableElements = persons,
        placeholder = {
            Text("Search...")
        },
        icon = Icons.Default.Search,
    )
}
