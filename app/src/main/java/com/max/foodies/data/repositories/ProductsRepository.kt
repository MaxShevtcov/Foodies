package com.max.foodies.data.repositories

import com.max.foodies.data.network.pojo.Product
import com.max.foodies.data.room.roomDatabase.DbProduct
import com.max.foodies.screens.UiProduct

interface ProductsRepository {
    suspend fun getProducts(forceUpdate: Boolean): List<UiProduct>

    suspend fun insertAll(products: List<DbProduct>)

     suspend fun getProductsFormApi(): List<Product>

     suspend fun getProductsFormDb(): List<DbProduct>

    suspend fun getProductById(id:Int): UiProduct
}