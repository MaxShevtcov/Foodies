package com.max.foodies.screens.searchScreen

import com.max.foodies.data.network.pojo.Product

data class SearchUiState(
    val products: List<Product> = emptyList(),
    val isSearching: Boolean = false,
    val searchText: String = "",
)