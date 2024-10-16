package com.max.foodies.data.mappers

import com.max.foodies.data.room.roomDatabase.ProductWithCountInCart
import com.max.foodies.screens.UiCartProduct

fun ProductWithCountInCart.toUiProductInCart(): UiCartProduct {
    return UiCartProduct(
        product = product.toUiProduct(),
        cartCounter = cartCounter.productCount
    )

}