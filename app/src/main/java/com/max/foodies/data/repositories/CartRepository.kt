package com.max.foodies.data.repositories

import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.screens.UiCartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun insertProductInCart(dbCartCounter: DbCartCounter)

    suspend fun getProductAndCountInCart(): List<UiCartProduct>

    fun checkCartEmpty(): Flow<Boolean>
}