package com.max.foodies.data.room.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbCartProduct(
    @PrimaryKey(autoGenerate = false)
    val productId: Int? = null,
    @ColumnInfo(name = "product_count")
    val productCount: Int? = null
)