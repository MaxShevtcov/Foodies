package com.max.foodies.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.max.foodies.screens.ProductsList
import com.max.foodies.screens.searchScreen.composeItems.SearchScreenTopBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToProduct: (id:Int?) -> Unit,
) {
    val uiProducts = searchViewModel.uiProducts.collectAsState()
    val searchText = searchViewModel.searchText.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        SearchScreenTopBar(
            searchText = searchText.value,
            isSearching = isSearching.value,
            onSearchTextChange = { query -> searchViewModel.onSearchTextChange(query) },
            onToogleSearch = { searchViewModel.onToogleSearch() },
            onBackPressed = { onBackPressed() },
            modifier = modifier
                .height(72.dp)
                .clip(GenericShape { size, _ ->
                    lineTo(size.width, 0f)
                    lineTo(size.width, Float.MAX_VALUE)
                    lineTo(0f, Float.MAX_VALUE)
                })
                .shadow(elevation = 4.dp)
                .background(color = MaterialTheme.colorScheme.background)
        )
        if (searchText.value.isNotBlank()) {
            ProductsList(
                modifier = modifier,
                products = uiProducts.value,
                onNavigateToProduct = onNavigateToProduct,
                onAddProductToCart = {uiProduct -> searchViewModel.addProductToCart(uiProduct)},
                onTakeProductFromCart = {uiproduct -> searchViewModel.takeProductFromCart(uiproduct)}
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
    SearchScreen(onBackPressed = {}, onNavigateToProduct = {})
}