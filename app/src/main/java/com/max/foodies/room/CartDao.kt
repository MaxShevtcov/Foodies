package com.max.foodies.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface CartDao {
    @Insert
    suspend fun insert(productInCart: ProductInCart)

    @Delete
    suspend fun delete(productInCart:ProductInCart)
}