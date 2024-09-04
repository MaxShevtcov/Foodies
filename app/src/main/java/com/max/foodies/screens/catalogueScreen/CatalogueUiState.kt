package com.max.foodies.screens.catalogueScreen

import com.max.foodies.network.pojo.Category
import com.max.foodies.network.pojo.Product

data class CatalogueState(
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isSearching: Boolean = false,
    val searchText: String = "",
    )