package com.max.foodies.screens.catalogue

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueSearchBar(
    searchText: String,
    isSearching: Boolean,
    onSearchTextChange: (String) -> Unit,
    onToogleSearch:()-> Unit,
    modifier: Modifier
) {


    SearchBar(
        query = searchText,
        onQueryChange = { query ->
            onSearchTextChange(query)
        },
        onSearch = { query ->
            onSearchTextChange(query)
        },
        active = isSearching,
        onActiveChange = { onToogleSearch },
        modifier = modifier,
        placeholder = { Text("Search") },
    ) {

    }
}
