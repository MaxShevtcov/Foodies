package com.max.foodies.mappers

import com.max.foodies.network.pojo.Product
import com.max.foodies.room.catalogueDatabase.ProductInCatalogue

fun ProductInCatalogue.toProduct(): Product {
    return Product(
        id = id,
        categoryId = categoryId,
        name = name,
        description = description,
        image = image,
        priceCurrent = priceCurrent,
        priceOld = priceOld,
        measure = measure,
        measureUnit = measureUnit,
        energyPer100Grams = energyPer100Grams,
        proteinsPer100Grams = proteinsPer100Grams,
        fatsPer100Grams = fatsPer100Grams,
        carbohydratesPer100Grams = carbohydratesPer100Grams,
        tagIds = tagIds
    )
}