package com.max.foodies.screens.cartScreen.composeItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max.foodies.screens.UiCartProduct

@Composable
fun CartProductsList(
    modifier: Modifier,
    products: List<UiCartProduct>,
    onNavigateToProduct: (id:Int?) -> Unit
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = products,
            key = { product -> product.product.id!! }
        ) { product ->
            CartProductCard(modifier = modifier.clickable { onNavigateToProduct(product.product.id) }, product = product,)

        }
    }
}
