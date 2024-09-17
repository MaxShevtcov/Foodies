package com.max.foodies.data.mappers

import com.max.foodies.data.network.pojo.Category
import com.max.foodies.data.room.roomDatabase.DbCategory
import com.max.foodies.screens.UiCategory

fun DbCategory.toUiCategory(): UiCategory {
    return UiCategory(
        id = id,
        name = name
    )
}