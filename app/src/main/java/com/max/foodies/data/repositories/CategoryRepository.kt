package com.max.foodies.data.repositories

import com.max.foodies.data.network.pojo.Category
import com.max.foodies.data.room.roomDatabase.DbCategory
import com.max.foodies.screens.UiCategory

interface CategoryRepository {
    suspend fun getCategories(forceUpdate: Boolean): List<UiCategory>

    suspend fun insertAll(categories: List<DbCategory>)

    suspend fun getCategoriesFormApi(): List<Category>

    suspend fun getCategoriesFormDb(): List<DbCategory>
}