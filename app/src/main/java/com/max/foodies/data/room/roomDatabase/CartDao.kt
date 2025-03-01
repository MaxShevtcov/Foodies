package com.max.foodies.data.room.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbProduct: DbProduct)

    @Delete
    suspend fun delete(dbProduct: DbProduct)

    @Query("SELECT * FROM dbproduct WHERE count_in_cart != 0")
    suspend fun getProductInCart(): List<DbProduct>

}