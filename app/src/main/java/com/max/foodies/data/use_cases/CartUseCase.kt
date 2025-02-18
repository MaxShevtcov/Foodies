package com.max.foodies.data.use_cases

import com.max.foodies.data.mappers.toDbProduct
import com.max.foodies.data.mappers.toUiProduct
import com.max.foodies.data.repositories.CartRepository
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend fun getProductsInCart(): List<UiProduct> {
        return cartRepository.getProductInCart().map { it.toUiProduct() }
    }

//    suspend fun checkCartEmpty(): Flow<Boolean> =
//        cartRepository.getProductInCart().map { it.countInCart != null }.isEmpty().asFlow()

    suspend fun countBillSum(): Int {
        val productsInCartList = getProductsInCart()
        var cartBillSum = 0

        productsInCartList.forEach {
            cartBillSum += it.priceCurrent?.times(it.countInCart ?: 0) ?: 0

        }
        return cartBillSum
    }

    suspend fun addProductInCart(item: UiProduct) {
        val prevCounter = item.countInCart ?: 0
        var currentCounter = prevCounter
        currentCounter += 1
        val dbProduct = item.toDbProduct(currentCounter)
        cartRepository.insertProductInCart(dbProduct)
    }

    suspend fun takeProductFromCart(item: UiProduct) {
        val prevCounter = item.countInCart ?: 0
        var currentCounter = prevCounter
        if (prevCounter > 0) {
            currentCounter -= 1
            val dbProduct = item.toDbProduct(currentCounter)
            cartRepository.insertProductInCart(dbProduct)
        } else {
            cartRepository.delete(item.toDbProduct(prevCounter))
        }

    }
}
