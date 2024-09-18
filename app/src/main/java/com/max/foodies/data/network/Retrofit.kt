package com.max.foodies.data.network

import com.max.foodies.data.network.pojo.Category
import com.max.foodies.data.network.pojo.Product
import com.max.foodies.data.network.pojo.Tag
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://anika1d.github.io/WorkTestServer/"
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

object Retrofit {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val categoriesApi: CategoriesApi by lazy {
        retrofit.create(CategoriesApi::class.java)
    }
    val productsApi: ProductsApi by lazy {
        retrofit.create(ProductsApi::class.java)
    }
    val tagsApi: TagsApi by lazy {
        retrofit.create(TagsApi::class.java)
    }
}