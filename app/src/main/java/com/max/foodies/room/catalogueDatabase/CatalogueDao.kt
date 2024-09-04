package com.max.foodies.room.catalogueDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.max.foodies.network.pojo.Product

@Dao
interface CatalogueDao {
    @Insert
    suspend fun insert(productInCart: Product)

    @Delete
    suspend fun delete(productInCart:Product)

    @Query("DELETE FROM catalogue_table")
    suspend fun deleteAll()
}