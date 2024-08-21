package com.max.foodies.screens.catalogue

import com.max.foodies.network.pojo.Category
import com.max.foodies.network.pojo.Product

data class CatalogueState(
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    )