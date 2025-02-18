package com.max.foodies.data.repositories

import android.util.Log
import com.max.foodies.data.mappers.toDbProduct
import com.max.foodies.data.mappers.toUiProduct
import com.max.foodies.data.network.ProductsApi
import com.max.foodies.data.network.pojo.Product
import com.max.foodies.data.room.roomDatabase.ProductDao
import com.max.foodies.data.room.roomDatabase.DbProduct
import com.max.foodies.screens.UiProduct
import javax.inject.Inject


class ProductsRepository @Inject constructor(
    private val networkDataSource: ProductsApi,
    private val localDataSource: ProductDao
) {
    suspend fun getProducts(forceUpdate: Boolean): List<UiProduct> {
        return if (forceUpdate) {
            val products = getProductsFormApi()
            insertAll(products.map { it.toDbProduct() })
            products.map { it.toUiProduct() }
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

    suspend fun getProductById(id:Int): UiProduct {
        return localDataSource.getById(id).toUiProduct()
    }
}


