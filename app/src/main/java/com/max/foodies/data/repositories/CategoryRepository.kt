package com.max.foodies.data.repositories


import android.util.Log
import com.max.foodies.data.mappers.toDbCategory
import com.max.foodies.data.mappers.toUiCategory
import com.max.foodies.data.network.CategoriesApi
import com.max.foodies.data.network.pojo.Category
import com.max.foodies.data.room.roomDatabase.CategoryDao
import com.max.foodies.data.room.roomDatabase.DbCategory
import com.max.foodies.screens.UiCategory
import javax.inject.Inject


class CategoryRepository @Inject constructor(
    private val networkDataSource: CategoriesApi,
    private val localDataSource: CategoryDao
) {
//    suspend fun getCategories(): List<UiCategory> {
//        try {
//            return networkDataSource.getCategories().map { it.toUiCategory() }
//        } catch (e: Exception) {
//            Log.e("!!!", "$e")
//        }
//        return emptyList()
//    }

    suspend fun getCategories(forceUpdate: Boolean): List<UiCategory> {
        return if (forceUpdate) {
            val categories = getCategoriesFormApi()
            insertAll(categories.map { it.toDbCategory() })
            return categories.map { it.toUiCategory() }
        } else {
            getCategoriesFormDb().map { it.toUiCategory() }
        }
    }

    private suspend fun insertAll(categories: List<DbCategory>) {
        localDataSource.insertAll(categories)
    }

    private suspend fun getCategoriesFormApi(): List<Category> {
        try {
            return networkDataSource.getCategories()
        } catch (e: Exception) {
            Log.e("!!!", "$e")
        }
        return emptyList()
    }

    private suspend fun getCategoriesFormDb(): List<DbCategory> {
        return localDataSource.get()
    }
}

