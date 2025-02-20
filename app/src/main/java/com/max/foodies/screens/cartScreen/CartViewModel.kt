package com.max.foodies.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.use_cases.CartUseCase
import com.max.foodies.screens.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel() {

    private val _uiCartProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())
    val uiCartProducts: StateFlow<List<UiProduct>> = _uiCartProducts.asStateFlow()

    init {
        viewModelScope.launch {
            val cartProducts = updateCartProducts()
            _uiCartProducts.update {
                cartProducts
            }
        }

    }

    private suspend fun updateCartProducts(): List<UiProduct> {
        return cartUseCase.getProductsInCart()
    }

    fun addProductToCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.addProductInCart(item)

            val cartProducts = updateCartProducts()
            _uiCartProducts.update {
                cartProducts
            }
        }
    }

    fun takeProductFromCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.takeProductFromCart(item)

            val cartProducts = updateCartProducts()
            _uiCartProducts.update {
                cartProducts
            }
        }
    }
}