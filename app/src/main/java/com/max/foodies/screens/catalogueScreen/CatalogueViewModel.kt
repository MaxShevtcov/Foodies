package com.max.foodies.screens.catalogueScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.CartRepository
import com.max.foodies.data.CategoryRepository
import com.max.foodies.data.ProductsRepository
import com.max.foodies.data.mappers.toDbCartCounter
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
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())
    val uiProducts: StateFlow<List<UiProduct>> = _uiProducts.asStateFlow()

    private val _uiCategories: MutableStateFlow<List<UiCategory>> =
        MutableStateFlow(emptyList())
    val uiCategories: StateFlow<List<UiCategory>> = _uiCategories.asStateFlow()
    private val _isCartEmpty: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isCartEmpty: StateFlow<Boolean> = _isCartEmpty.asStateFlow()

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
        viewModelScope.launch {
            cartRepository.checkCartEmpty().collect { value -> _isCartEmpty.update { value } }
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

    fun selectCategory(item: UiCategory, selected: Boolean) {
        _uiCategories.value.find { it.id == item.id }?.let { category ->
            category.selected = selected
            filterProducts(category, selected)
        }
    }

    fun addProductToCart(item: UiProduct) {
        viewModelScope.launch {
            cartRepository.insertProductInCart(item.toDbCartCounter())
        }
    }
}