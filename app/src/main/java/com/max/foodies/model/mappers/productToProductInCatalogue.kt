package com.max.foodies.model.mappers

import com.max.foodies.model.network.pojo.Product
import com.max.foodies.model.room.catalogueDatabase.ProductInCatalogue

fun Product.toProductInCatalogue(): ProductInCatalogue {
    return ProductInCatalogue(
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