package com.max.foodies.data.mappers

import com.max.foodies.data.network.pojo.Category
import com.max.foodies.data.room.roomDatabase.DbCategory
import com.max.foodies.screens.UiCategory

fun Category.toDbCategory(): DbCategory {
    return DbCategory(
        id = id,
        name = name
    )
}