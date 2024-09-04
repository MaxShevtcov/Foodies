package com.max.foodies.room.catalogueDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import com.max.foodies.network.pojo.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productInCart: ProductInCatalogue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductInCatalogue>)

    @Delete
    suspend fun delete(productInCatalogue:ProductInCatalogue)

    @Query("SELECT * FROM catalogue_table")
    fun get(): List<ProductInCatalogue>

    @Query("DELETE FROM catalogue_table")
    suspend fun deleteAll()
}