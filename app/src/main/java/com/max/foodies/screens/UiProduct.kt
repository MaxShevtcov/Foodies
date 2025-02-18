package com.max.foodies.screens


data class UiProduct(
    val id: Int? = null,
    val categoryId: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val priceCurrent: Int? = null,
    val priceOld: String? = null,
    val measure: Int? = null,
    val measureUnit: String? = null,
    val energyPer100Grams: Double? = null,
    val proteinsPer100Grams : Double? = null ,
    val fatsPer100Grams : Double? = null ,
    val carbohydratesPer100Grams : Double? = null ,
    var tagIds: List<String>? = emptyList(),
    val countInCart:Int? = null
)