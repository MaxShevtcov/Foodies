package com.max.foodies.screens.catalogueScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.repositories.CategoryRepository
import com.max.foodies.data.repositories.ProductsRepository
import com.max.foodies.data.use_cases.CartUseCase
import com.max.foodies.screens.UiCategory
import com.max.foodies.screens.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogueViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val categoryRepository: CategoryRepository,
    private val cartUseCase: CartUseCase
) : ViewModel() {

    private val _uiProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())
    val uiProducts: StateFlow<List<UiProduct>> = _uiProducts.asStateFlow()

    private val _uiCategories: MutableStateFlow<List<UiCategory>> =
        MutableStateFlow(emptyList())
    val uiCategories: StateFlow<List<UiCategory>> = _uiCategories.asStateFlow()

    private val _isCartEmpty: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isCartEmpty: StateFlow<Boolean> = _isCartEmpty.asStateFlow()

    private val _cartBill: MutableStateFlow<Int> = MutableStateFlow(0)
    val cartBill: StateFlow<Int> = _cartBill.asStateFlow()


    private val concatenatedFilteredProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val uiCategories = updateCategories(true)
            _uiCategories.update {
                uiCategories
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val uiProducts = updateProducts(true)
            _uiProducts.update {
                uiProducts
            }
        }

    }

    private suspend fun updateProducts(forceUpdate: Boolean): List<UiProduct> {
        return productsRepository.getProducts(forceUpdate)
    }

    private suspend fun updateCategories(forceUpdate: Boolean): List<UiCategory> {
        return categoryRepository.getCategories(forceUpdate)
    }

    private fun filterProducts(category: UiCategory, selected: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            val products = updateProducts(forceUpdate = false)
            val filteredProducts = products.filter { product -> product.categoryId == category.id }

            if (selected) {
                concatenatedFilteredProducts.value =
                    concatenatedFilteredProducts.value.plus(filteredProducts)
            } else {
                concatenatedFilteredProducts.value =
                    concatenatedFilteredProducts.value.minus(filteredProducts.toSet())
            }
            _uiProducts.update {
                concatenatedFilteredProducts.value.ifEmpty { products }
            }
        }
    }

    private suspend fun updateCartBill() {
        val cartBillSum = cartUseCase.countBillSum()
        _cartBill.update {
            cartBillSum
        }
    }

    private fun checkCartEmpty() {
        viewModelScope.launch {
            val isCartEmpty =
                cartUseCase.getProductsInCart()
                    .map { it.countInCart != 0 }
                    .isEmpty()
            _isCartEmpty.update {
                isCartEmpty
            }
            Log.e("!!!", "_isCartEmpty in catalogueVM ${_isCartEmpty.value}")
        }

    }

    fun selectCategory(item: UiCategory, selected: Boolean) {
        _uiCategories.value.find { it.id == item.id }?.let { category ->
            category.selected = selected
            filterProducts(category, selected)
        }
    }

    fun addProductToCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.addProductInCart(item)
            val uiProducts = updateProducts(false)
            _uiProducts.update {
                uiProducts
            }
            launch { updateCartBill() }
            launch { checkCartEmpty() }
        }
    }

    fun takeProductFromCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.takeProductFromCart(item)
            val uiProducts = updateProducts(false)
            _uiProducts.update {
                uiProducts
            }
            launch { updateCartBill() }
            launch { checkCartEmpty() }
        }
    }
}