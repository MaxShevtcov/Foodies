package com.max.foodies.data.room.cartDatabase

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class ProductConverters {
    @TypeConverter
    fun fromTagIds(tagIds: List<String>?): String? {
//        return tagIds?.stream()?.collect(Collectors.joining(","))
        return tagIds?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toTagIds(string: String?): List<String>? {
        return string?.split(",")
    }
}