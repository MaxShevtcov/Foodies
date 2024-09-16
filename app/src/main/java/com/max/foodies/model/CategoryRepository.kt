package com.max.foodies.model


import android.util.Log
import com.max.foodies.model.network.CategoriesApi
import com.max.foodies.model.network.pojo.Category


class CategoryRepository(
    private val networkDataSource: CategoriesApi
) {
     suspend fun getCategories(): List<Category> {
        try {
            return networkDataSource.getCategories()
        } catch (e: Exception) {
            Log.e("!!!", "$e")
        }
        return emptyList()
    }
}

