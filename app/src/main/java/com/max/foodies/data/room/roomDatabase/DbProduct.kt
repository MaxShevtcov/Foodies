package com.max.foodies.data.room.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.max.foodies.data.room.ProductConverters

@Entity
@TypeConverters(ProductConverters::class)
data class DbProduct(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    @ColumnInfo("category_id")
    val categoryId: Int? = null,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("description")
    val description: String? = null,
    @ColumnInfo("image")
    val image: String? = null,
    @ColumnInfo("price_current")
    val priceCurrent: Int? = null,
    @ColumnInfo("price_old")
    val priceOld: String? = null,
    @ColumnInfo("measure")
    val measure: Int? = null,
    @ColumnInfo("measure_unit")
    val measureUnit: String? = null,
    @ColumnInfo("energy_per_100_grams")
    val energyPer100Grams: Double? = null,
    @ColumnInfo("proteins_per_100_grams")
    val proteinsPer100Grams: Double? = null,
    @ColumnInfo("fats_per_100_grams")
    val fatsPer100Grams: Double? = null,
    @ColumnInfo("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double? = null,
    @ColumnInfo("tag_ids")
    var tagIds: List<String>? = emptyList(),
)