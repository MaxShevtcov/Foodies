package com.max.foodies.data.repositories

import com.max.foodies.data.mappers.toUiProductInCart
import com.max.foodies.data.room.roomDatabase.CartDao
import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.screens.UiCartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl@Inject constructor(
    private val localCartDataSource: CartDao
):CartRepository {


    override suspend fun insertProductInCart(dbCartCounter: DbCartCounter) {
        val dbCartCounter = dbCartCounter
        localCartDataSource.insert(dbCartCounter)
    }

    override suspend fun getProductAndCountInCart(): List<UiCartProduct> {
        return localCartDataSource.getProductAndCountInCart().map { it.toUiProductInCart() }
    }



    override fun checkCartEmpty():Flow<Boolean> {
        return localCartDataSource.getCartCounter().map { cart ->
            cart.isNullOrEmpty()
        }
    }
}