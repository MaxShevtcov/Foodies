package com.max.foodies.data.mappers

import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.data.room.roomDatabase.ProductWithCountInCart
import com.max.foodies.screens.UiCartProduct
import com.max.foodies.screens.UiProduct

fun UiProduct.toDbCartCounter(): DbCartCounter {
    return DbCartCounter(
        productId = id,
        productCount = 1
    )

}