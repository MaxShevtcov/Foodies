package com.max.foodies.screens.catalogue

import com.max.foodies.network.pojo.Categorie
import com.max.foodies.network.pojo.Product

data class CatalogueState(
    val product: List<Product> = emptyList(),
    val categories: List<Categorie> = emptyList(),
    )