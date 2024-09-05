package com.max.foodies.screens.catalogueScreen

import com.max.foodies.model.network.pojo.Category
import com.max.foodies.model.network.pojo.Product

data class CatalogueState(
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),

    )