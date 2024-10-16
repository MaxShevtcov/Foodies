package com.max.foodies.data.room.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbCartCounter: DbCartCounter)

    @Query("SELECT * FROM dbproduct" + " JOIN dbCartCounter ON dbProduct.id = dbCartCounter.productId")
    suspend fun getProductAndCountInCart(): List<ProductWithCountInCart>

    @Query("SELECT * FROM dbCartCounter")
     fun getCartCounter(): Flow<List<DbCartCounter>>

    @Query("DELETE FROM dbCartCounter")
    suspend fun deleteAll()
}