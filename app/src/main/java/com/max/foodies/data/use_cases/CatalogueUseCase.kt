package com.max.foodies.data.use_cases

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.repositories.CategoryRepository
import com.max.foodies.data.repositories.ProductsRepository
import com.max.foodies.screens.UiCategory
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogueUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {

    fun getProducts(forceUpdate: Boolean): Flow<List<UiProduct>> {
        val products = productsRepository.getProducts(forceUpdate)
        return products

    }
    fun getProductsByCategory(categoryId:Int?): Flow<List<UiProduct>> {
        val products = productsRepository.getProducts(false).map { products ->
            products.filter { product -> product.categoryId == categoryId }
        }
        return products

    }



}