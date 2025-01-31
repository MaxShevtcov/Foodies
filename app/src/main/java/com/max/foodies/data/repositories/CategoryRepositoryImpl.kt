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


class CategoryRepositoryImpl@Inject constructor(
    private val networkDataSource: CategoriesApi,
    private val localDataSource: CategoryDao
):CategoryRepository {

    override suspend fun getCategories(forceUpdate: Boolean): List<UiCategory> {
        return if (forceUpdate) {
            val categories = getCategoriesFormApi()
            insertAll(categories.map { it.toDbCategory() })
            return categories.map {it.toUiCategory()}
        } else {
            getCategoriesFormDb().map { it.toUiCategory() }
        }
    }

    override suspend fun insertAll(categories: List<DbCategory>) {
        localDataSource.insertAll(categories)
    }

    override suspend fun getCategoriesFormApi(): List<Category> {
        try {
            return networkDataSource.getCategories()
        } catch (e: Exception) {
            Log.e("!!!", "$e")
        }
        return emptyList()
    }

    override suspend fun getCategoriesFormDb(): List<DbCategory> {
        return localDataSource.get()
    }
}

