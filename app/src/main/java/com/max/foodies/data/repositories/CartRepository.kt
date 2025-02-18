package com.max.foodies.data.repositories

import com.max.foodies.data.mappers.toUiProduct
import com.max.foodies.data.mappers.toUiProductInCart
import com.max.foodies.data.room.roomDatabase.CartDao
import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.data.room.roomDatabase.DbProduct
import com.max.foodies.screens.UiCartCounter
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val localCartDataSource: CartDao
) {
    suspend fun delete(dbProduct: DbProduct) {
        localCartDataSource.delete(dbProduct)
    }

    suspend fun insertProductInCart(dbProduct: DbProduct) {
        localCartDataSource.insert(dbProduct)
    }

    suspend fun getProductInCart(): List<DbProduct> {
        return localCartDataSource.getProductInCart()
    }

}