package com.max.foodies.model.network.pojo

import com.google.gson.annotations.SerializedName


data class Tag (

    @SerializedName("id")
    val id   : Int?    = null,
    @SerializedName("name" )
    val name : String? = null

)