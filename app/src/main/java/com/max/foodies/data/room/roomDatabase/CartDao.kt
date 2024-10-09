package com.max.foodies.data.room.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbCartProduct: DbCartProduct)

    @Query("SELECT * FROM dbproduct" + " JOIN dbCartProduct ON dbProduct.id = dbCartProduct.productId")
    suspend fun getProductAndCountInCart(): Map<DbProduct, DbCartProduct>

    @Query("DELETE FROM dbCartProduct")
    suspend fun deleteAll()
}