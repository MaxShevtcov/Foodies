package com.max.foodies.data.mappers

import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.data.room.roomDatabase.ProductWithCountInCart
import com.max.foodies.screens.UiCartProduct

fun UiCartProduct.toDbCartCounter(): DbCartCounter {
    return DbCartCounter(
        productId = product.id,
        productCount = cartCounter
    )

}