package com.max.foodies.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.max.foodies.screens.ProductsList

@Composable
fun SearchScreen(
    searchScreenViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchScreenState = searchScreenViewModel.searchUiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        SearchScreenTopBar(
            searchText = searchScreenState.value.searchText,
            isSearching = searchScreenState.value.isSearching,
            onSearchTextChange = { query -> searchScreenViewModel.onSearchTextChange(query) },
            onToogleSearch = { searchScreenViewModel.onToogleSearch() },
            onBackPressed = { onBackPressed() },
            modifier = modifier
                .height(72.dp)
                .shadow(4.dp)
                .background(color = Color.White)
        )
        if (searchScreenState.value.searchText.isNotBlank()) {
            ProductsList(
                modifier = modifier,
                products = searchScreenState.value.products
            )
        } else {
            Text(
                text = "Введите название блюда,\n которое ищите",
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                softWrap = true,
                minLines = 2
            )
        }
    }

}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(onBackPressed = {})
}