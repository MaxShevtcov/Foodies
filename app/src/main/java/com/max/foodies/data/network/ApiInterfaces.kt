package com.max.foodies.data.network

import com.max.foodies.data.network.pojo.Category
import com.max.foodies.data.network.pojo.Product
import com.max.foodies.data.network.pojo.Tag
import retrofit2.http.GET

interface CategoriesApi {
    @GET("Categories.json")
    suspend fun getCategories(): List<Category>
}
interface ProductsApi {
    @GET("Products.json")
    suspend fun getProducts(): List<Product>
}
interface TagsApi {
    @GET("Tag.json")
    suspend fun getTags(): List<Tag>
}

