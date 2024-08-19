package com.max.foodies.screens.catalogue

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max.foodies.network.pojo.Product

@Composable
fun ProductsList(modifier: Modifier, products: List<Product>) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)
    ) {
        items(
            items = products,
            key = {product -> product.id!!}
        ) { product ->
        ProductCard(modifier = modifier, product = product)
    }
    }
}
