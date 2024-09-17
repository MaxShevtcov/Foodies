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

        tagIds = tagIds
    )
}