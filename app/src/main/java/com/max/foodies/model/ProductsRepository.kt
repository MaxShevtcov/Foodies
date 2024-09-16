package com.max.foodies.model

import android.util.Log
import com.max.foodies.model.network.ProductsApi
import com.max.foodies.model.network.pojo.Category
import com.max.foodies.model.network.pojo.Product
import com.max.foodies.model.room.catalogueDatabase.CatalogueDao
import com.max.foodies.model.room.catalogueDatabase.ProductInCatalogue


class ProductsRepository(
    private val networkDataSource: ProductsApi,
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

    suspend fun getProductsFormDb(): List<ProductInCatalogue> {
        return localDataSource.get()
    }
}

