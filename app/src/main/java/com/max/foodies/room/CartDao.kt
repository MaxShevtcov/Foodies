package com.max.foodies.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert
    suspend fun insert(productInCart: ProductInCart)

    @Delete
    suspend fun delete(productInCart:ProductInCart)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAll()
}