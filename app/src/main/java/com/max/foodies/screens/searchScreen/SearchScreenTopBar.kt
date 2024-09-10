package com.max.foodies.screens.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

import com.max.foodies.ui.theme.ComposeIcon
import com.max.foodies.ui.theme.ComposeIcons

@Composable
fun SearchScreenTopBar(
    searchText: String,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    onToogleSearch: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier
) {


    Row(modifier = modifier) {
        IconButton(
            modifier = modifier,
            colors = IconButtonDefaults.iconButtonColors()
                .copy(containerColor = MaterialTheme.colorScheme.background),
            onClick = { onBackPressed() }
        ) {
            ComposeIcon(resourceId = ComposeIcons.arrowLeft, annotation = "filter")
        }
        SearchScreenSearchBar(
            searchText = searchText,
            isSearching = isSearching,
            onSearchTextChange = onSearchTextChange,
            onToogleSearch = onToogleSearch,
            tonalElavation = 0.dp,
            modifier = modifier
        )

    }
}


//@Preview(showBackground = true, widthDp = 320, heightDp = 64)
//@Composable
//fun CatalogueTopBarPreview() {
//    CatalogueTopBar(Modifier)
//}