package com.max.foodies.data.mappers

import com.max.foodies.data.room.roomDatabase.DbCartProduct
import com.max.foodies.screens.UiCartProduct

fun UiCartProduct.toDbCartProduct(): DbCartProduct {
    return DbCartProduct(
        productId = productId,
        productCount = productCount
    )

}