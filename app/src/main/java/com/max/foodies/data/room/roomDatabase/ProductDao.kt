package com.max.foodies.data.room.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productInCart: DbProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<DbProduct>)

    @Delete
    suspend fun delete(dbProduct: DbProduct)

    @Query("SELECT * FROM dbProduct_table")
    suspend fun get(): List<DbProduct>

    @Query("DELETE FROM dbProduct_table")
    suspend fun deleteAll()
}