package com.max.foodies.screens.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.max.foodies.ui.theme.ComposeIcon
import com.max.foodies.ui.theme.ComposeIcons

@Composable
fun SearchScreenTopBar(
    searchText: String,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit ,
    onToogleSearch: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier
) {
    
    Column {
        Row() {
            IconButton(
                modifier = modifier.padding(start = 8.dp),
                onClick = {onBackPressed()}
            ) {
                ComposeIcon(resourceId = ComposeIcons.arrowLeft, annotation = "filter")
            }
            SearchScreenSearchBar(
                searchText = searchText,
                isSearching = isSearching,
                onSearchTextChange = onSearchTextChange,
                onToogleSearch = onToogleSearch,
                modifier = modifier
            )

        }
    }
}


//@Preview(showBackground = true, widthDp = 320, heightDp = 64)
//@Composable
//fun CatalogueTopBarPreview() {
//    CatalogueTopBar(Modifier)
//}