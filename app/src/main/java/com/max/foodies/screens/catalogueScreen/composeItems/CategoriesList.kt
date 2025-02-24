package com.max.foodies.screens.catalogueScreen.composeItems

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.max.foodies.screens.UiCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun CategoriesList(
    modifier: Modifier,
    categories: List<UiCategory>,
    onSelected: (UiCategory) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow() {
        items(
            items = categories,
            key = { category -> category.id!! }
        ) { category ->
            CategoryButton(
                modifier = modifier,
                category = category,
                selected = category.selected,
                onSelected = {
                    onSelected(category)
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )

        }
    }

}