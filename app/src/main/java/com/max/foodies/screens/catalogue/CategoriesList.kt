package com.max.foodies.screens.catalogue

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max.foodies.network.pojo.Category

@Composable
fun CategoriesList(
    modifier: Modifier,
    categories: List<Category>,
    onSelected: (Category, Boolean) -> Unit
) {
    LazyRow() {
        items(
            items = categories,
            key = { category -> category.id!! }
        ) { category ->
            CategoryButton(
                modifier = modifier,
                category = category,
                onSelected = onSelected,
                selected = category.selected
            )

        }
    }

}