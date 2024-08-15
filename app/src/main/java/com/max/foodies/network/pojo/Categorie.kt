package com.max.foodies.network.pojo

import com.google.gson.annotations.SerializedName


data class Categorie (

    @SerializedName("id"   )
    val id   : Int?    = null,
    @SerializedName("name" )
    val name : String? = null

)
