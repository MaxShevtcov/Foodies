package com.max.foodies.screens.cartScreen.composeItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max.foodies.screens.UiProduct

@Composable
fun CartProductsList(
    modifier: Modifier,
    products: List<UiProduct>,
    onNavigateToProduct: (id: Int?) -> Unit,
    onAddProductToCart: (uiProduct: UiProduct) -> Unit,
    onTakeProductFromCart: (uiProduct: UiProduct) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = products,
            key = { product -> product.id!! }
        ) { product ->
            CartProductCard(
                modifier = modifier.clickable { onNavigateToProduct(product.id) },
                product = product,
                onAddProductToCart,
                onTakeProductFromCart
            )

        }
    }
}
