package com.max.foodies.data.network.pojo


import com.google.gson.annotations.SerializedName


data class Category(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
)
