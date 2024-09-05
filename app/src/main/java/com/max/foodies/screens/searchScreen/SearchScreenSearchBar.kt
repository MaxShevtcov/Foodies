package com.max.foodies.screens.searchScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenSearchBar(
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
