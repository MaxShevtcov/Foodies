package com.max.foodies.model.network

import com.max.foodies.model.network.pojo.Category
import com.max.foodies.model.network.pojo.Product
import com.max.foodies.model.network.pojo.Tag
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://anika1d.github.io/WorkTestServer/"
interface FoodiesApiService {
    @GET("Categories.json")
    suspend fun getCategories(): List<Category>
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
    val foodiesApiService: FoodiesApiService by lazy {
        retrofit.create(FoodiesApiService::class.java)
    }
}