package com.max.foodies.network.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("category_id")
    val categoryId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("price_current")
    val priceCurrent: Int? = null,
    @SerializedName("price_old")
    val priceOld: String? = null,
    @SerializedName("measure")
    val measure: Int? = null,
    @SerializedName("measure_unit")
    val measureUnit: String? = null,
    @SerializedName("energy_per_100_grams")
    val energyPer100Grams: Double? = null,
    @SerializedName("proteins_per_100_grams")
    val proteinsPer100Grams: Double? = null,
    @SerializedName("fats_per_100_grams")
    val fatsPer100Grams: Double? = null,
    @SerializedName("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double? = null,
    @SerializedName("tag_ids")
    var tagIds: List<String>? = emptyList(),
)