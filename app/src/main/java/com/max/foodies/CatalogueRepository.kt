package com.max.foodies

import android.util.Log
import com.max.foodies.network.FoodiesApiService
import com.max.foodies.network.pojo.Category
import com.max.foodies.network.pojo.Product
import com.max.foodies.room.CartDao
import com.max.foodies.room.ProductInCart
import com.max.foodies.room.catalogueDatabase.CatalogueDao
import com.max.foodies.room.catalogueDatabase.ProductInCatalogue


class CatalogueRepository(
    private val networkDataSource: FoodiesApiService,
    private val localDataSource: CatalogueDao
) {
    suspend fun insertAll(products: List<ProductInCatalogue>) {
        localDataSource.insertAll(products)
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

    suspend fun getProductsFormDb(): List<ProductInCatalogue> {
        return localDataSource.get()
    }
}

