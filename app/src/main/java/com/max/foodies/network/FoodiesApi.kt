package com.max.foodies.network

import com.max.foodies.network.pojo.Categorie
import com.max.foodies.network.pojo.Product
import com.max.foodies.network.pojo.Tag
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://anika1d.github.io/WorkTestServer/"
interface FoodiesApiService {
    @GET("Categorie.json")
    suspend fun getCategories(): List<Categorie>
    @GET("Tag.json")
    suspend fun getTags(): List<Tag>
    @GET("Products.json")
    suspend fun getProducts(): List<Product>
}

object FoodiesApi {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val foodiesApiService:FoodiesApiService by lazy {
        retrofit.create(FoodiesApiService::class.java)
    }
}