package br.com.handleservice.ui.components.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.ImeAction

@Composable
fun <T> HandleSearchBar(
    searchableElements: List<T> = emptyList(),
    placeholder: String,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant, // Alterado para MaterialTheme
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
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
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp), clip = false)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp)), // Alterado para MaterialTheme
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                viewModel.onSearchTextChange(it)
            },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface // Alterado para MaterialTheme
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = iconColor, // Alterado para MaterialTheme
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                placeholder,
                                color = MaterialTheme.colorScheme.onSurfaceVariant // Alterado para MaterialTheme
                            )
                        }
                        innerTextField()
                    }
                    if (active) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onValueChange("")
                                    viewModel.onSearchTextChange("")
                                },
                            tint = iconColor // Alterado para MaterialTheme
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearch()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
    }
}
