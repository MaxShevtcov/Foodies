package com.max.foodies.data

import android.util.Log
import com.max.foodies.data.mappers.toDbProduct
import com.max.foodies.data.mappers.toUiProduct
import com.max.foodies.data.network.ProductsApi
import com.max.foodies.data.network.pojo.Product
import com.max.foodies.data.room.roomDatabase.ProductDao
import com.max.foodies.data.room.roomDatabase.DbProduct
import com.max.foodies.screens.UiProduct


class ProductsRepository(
    private val networkDataSource: ProductsApi,
    private val localDataSource: ProductDao
) {
    suspend fun invoke(forceUpdate: Boolean): List<UiProduct> {
        return if (forceUpdate) {
            insertAll(getProductsFormApi().map { it.toDbProduct() })
            getProductsFormDb().map { it.toUiProduct() }
        } else {
            getProductsFormDb().map { it.toUiProduct() }
        }
    }

    private suspend fun insertAll(products: List<DbProduct>) {
        localDataSource.insertAll(products)
    }

    private suspend fun getProductsFormApi(): List<Product> {
        try {
            return networkDataSource.getProducts()
        } catch (e: Exception) {
            Log.e("!!!", "$e")
        }
        return emptyList()
    }

    private suspend fun getProductsFormDb(): List<DbProduct> {
        return localDataSource.get()
    }
}

