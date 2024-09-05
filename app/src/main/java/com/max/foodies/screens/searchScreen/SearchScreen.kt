package com.max.foodies.screens.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchScreen(
    searchScreenViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    modifier: Modifier
) {
    val searchScreenState = searchScreenViewModel.searchUiState.collectAsState()
    Column {
        SearchScreenTopBar(
            searchText = searchScreenState.value.searchText,
            isSearching = searchScreenState.value.isSearching,
            onSearchTextChange = { query -> searchScreenViewModel.onSearchTextChange(query) },
            onToogleSearch = { searchScreenViewModel.onToogleSearch() },
            onBackPressed = {/* TODO() */ },
            modifier = modifier.height(72.dp)
        )
        Text(text = "Введите название блюда, которое ищите")
    }

}