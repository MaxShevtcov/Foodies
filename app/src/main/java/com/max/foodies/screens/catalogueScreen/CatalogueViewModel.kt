package com.max.foodies.screens.catalogueScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.data.repositories.CartRepositoryImpl
import com.max.foodies.data.repositories.CategoryRepositoryImpl
import com.max.foodies.data.repositories.ProductsRepositoryImpl
import com.max.foodies.data.mappers.toDbCartCounter
import com.max.foodies.data.network.Retrofit
import com.max.foodies.data.room.roomDatabase.FoodiesDatabase
import com.max.foodies.screens.UiCategory
import com.max.foodies.screens.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogueViewModel @Inject constructor(
    private val productsRepositoryImpl: ProductsRepositoryImpl,
    private val categoryRepositoryImpl: CategoryRepositoryImpl,
    private val cartRepositoryImpl: CartRepositoryImpl
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
            cartRepositoryImpl.checkCartEmpty().collect { value -> _isCartEmpty.update { value } }
        }
    }

    private suspend fun updateProducts(forceUpdate: Boolean): List<UiProduct> {
        return productsRepositoryImpl.getProducts(forceUpdate)
    }

    private suspend fun updateCategories(forceUpdate: Boolean): List<UiCategory> {
        return categoryRepositoryImpl.getCategories(forceUpdate)
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
            cartRepositoryImpl.insertProductInCart(item.toDbCartCounter())
        }
    }
}