package com.max.foodies.data.mappers

import com.max.foodies.data.room.roomDatabase.DbProduct
import com.max.foodies.screens.UiProduct

fun DbProduct.toUiProduct(): UiProduct {
    return UiProduct(
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
        tagIds = tagIds,
        countInCart = countInCart
    )
}