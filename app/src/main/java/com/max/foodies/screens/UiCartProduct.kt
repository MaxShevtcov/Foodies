package com.max.foodies.screens

import androidx.room.Embedded
import com.max.foodies.data.room.roomDatabase.DbCartCounter
import com.max.foodies.data.room.roomDatabase.DbProduct

data class UiCartProduct(
    @Embedded
    val product: UiProduct,
    val cartCounter: Int?
)