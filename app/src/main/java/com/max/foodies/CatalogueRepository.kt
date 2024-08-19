package com.max.foodies

import android.util.Log
import com.max.foodies.network.FoodiesApiService
import com.max.foodies.network.pojo.Category
import com.max.foodies.network.pojo.Product
import com.max.foodies.room.CartDao
import com.max.foodies.room.ProductInCart

class CatalogueRepository(
    private val networkDataSource: FoodiesApiService,
    private val localDataSource: CartDao
) {
    suspend fun insert(product: ProductInCart) {
        localDataSource.insert(product)
    }
    suspend fun getProducts(): List<Product> {
        try {
            return networkDataSource.getProducts()
        } catch (e: Exception) {
            Log.e("!!!", "$e")
        }
        return emptyList()
    }

    suspend fun getCategories(): List<Category> {
        try {
            return networkDataSource.getCategories()
        } catch (e: Exception) {
            Log.e("!!!", "$e")
        }
        return emptyList()
    }
}