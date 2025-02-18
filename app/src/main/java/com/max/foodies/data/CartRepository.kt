package com.max.foodies.data

import android.util.Log
import com.max.foodies.data.mappers.toDbCartCounter
import com.max.foodies.data.mappers.toUiProductInCart
import com.max.foodies.data.room.roomDatabase.CartDao
import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.screens.UiCartProduct
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val localCartDataSource: CartDao
) {


    suspend fun insertProductInCart(dbCartCounter: DbCartCounter) {
        val dbCartCounter = dbCartCounter
        localCartDataSource.insert(dbCartCounter)
    }

    suspend fun getProductAndCountInCart(): List<UiCartProduct> {
        return localCartDataSource.getProductAndCountInCart().map { it.toUiProductInCart() }
    }


    fun checkCartEmpty(): Flow<Boolean> {
        return localCartDataSource.getCartCounter().map { cart ->
            cart.isEmpty()
        }
    }
}