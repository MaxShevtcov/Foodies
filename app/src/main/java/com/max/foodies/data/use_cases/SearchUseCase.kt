package com.max.foodies.data.use_cases

import com.max.foodies.data.repositories.ProductsRepository
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    fun searchProducts(prefix: CharSequence): Flow<List<UiProduct>> = flow {
        productsRepository.getProducts(false).transform { products ->
            products.filter { product ->
                product.name?.uppercase()?.contains(prefix) ?: false
            }
            emit(products)
        }
    }
}