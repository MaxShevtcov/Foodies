package com.max.foodies.data

import com.max.foodies.data.mappers.toDbCartProduct
import com.max.foodies.data.room.roomDatabase.CartDao
import com.max.foodies.data.room.roomDatabase.DbCartProduct
import com.max.foodies.data.room.roomDatabase.DbProduct
import com.max.foodies.screens.UiCartProduct

class CartRepository(
    //private val localProductDataSource: ProductDao,
    private val localCartDataSource: CartDao
) {
    suspend fun insertProductInCart(uiCartProduct: UiCartProduct) {
        val dbCartProduct = uiCartProduct.toDbCartProduct()
        localCartDataSource.insert(dbCartProduct)
    }

    suspend fun getProductAndCountInCart(): Map<DbProduct, DbCartProduct> {
        return localCartDataSource.getProductAndCountInCart()
    }
}