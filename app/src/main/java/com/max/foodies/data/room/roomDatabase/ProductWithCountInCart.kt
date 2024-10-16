package com.max.foodies.data.room.roomDatabase
// удалить ??
import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithCountInCart (
    @Embedded
    val product: DbProduct,
    @Relation(parentColumn = "id", entityColumn = "productId")
    val cartCounter: DbCartCounter
)