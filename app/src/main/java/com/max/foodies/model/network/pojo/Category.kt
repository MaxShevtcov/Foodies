package com.max.foodies.model.network.pojo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.annotations.SerializedName


data class Category(

    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,

    var initSelected: Boolean = false

) {
    var selected by mutableStateOf(initSelected)
}
