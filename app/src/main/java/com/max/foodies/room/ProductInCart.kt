package com.max.foodies.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.max.foodies.network.pojo.Product

@Entity(tableName = "cart_table")
@TypeConverters(ProductConverters::class)
data class ProductInCart(
    @PrimaryKey(autoGenerate = true)
    val key:Long = 0L,
    @Embedded(prefix = "product_")
    val product: Product,
    @ColumnInfo(name = "is_bought")
    val isBought: Boolean,
)