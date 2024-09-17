package com.max.foodies.screens.catalogueScreen.composeItems

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.max.foodies.data.network.pojo.Category
import com.max.foodies.screens.UiCategory

@Composable
fun CategoriesList(
    modifier: Modifier,
    categories: List<UiCategory>,
    onSelected: (UiCategory, Boolean) -> Unit
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