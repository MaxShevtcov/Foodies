package com.max.foodies.screens.catalogueScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.repositories.CategoryRepository
import com.max.foodies.data.repositories.ProductsRepository
import com.max.foodies.data.use_cases.CartUseCase
import com.max.foodies.data.use_cases.CatalogueUseCase
import com.max.foodies.screens.UiCategory
import com.max.foodies.screens.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogueViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val categoryRepository: CategoryRepository,
    private val cartUseCase: CartUseCase,
    private val catalogueUseCase: CatalogueUseCase
) : ViewModel() {

    private val _uiProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())
    //val uiProducts: StateFlow<List<UiProduct>> = _uiProducts.asStateFlow()

    private val _uiCategories: MutableStateFlow<List<UiCategory>> =
        MutableStateFlow(emptyList())
    val uiCategories: StateFlow<List<UiCategory>> = _uiCategories.asStateFlow()

    private val _isCartEmpty: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isCartEmpty: StateFlow<Boolean> = _isCartEmpty.asStateFlow()

    private val _cartBill: MutableStateFlow<Int> = MutableStateFlow(0)
    val cartBill: StateFlow<Int> = _cartBill.asStateFlow()


    private val _selectedCategory: MutableStateFlow<UiCategory> = MutableStateFlow(UiCategory())


    @OptIn(ExperimentalCoroutinesApi::class)
    val uiProducts: StateFlow<List<UiProduct>> = _selectedCategory.flatMapLatest { category ->

        if (category.id == null) {
            catalogueUseCase.getProducts(true)
        } else {
            catalogueUseCase.getProductsByCategory(category.id)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            val uiCategories = updateCategories(true)
            _uiCategories.update {
                uiCategories
            }
        }
    }


    private suspend fun updateCategories(forceUpdate: Boolean): List<UiCategory> {
        return categoryRepository.getCategories(forceUpdate)
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

    fun onCategorySelected(selectedItem: UiCategory) {
        selectedItem.selected = !selectedItem.selected
        val iterator = _uiCategories.value.listIterator()

        while (iterator.hasNext()) {
            val listItem = iterator.next()

            iterator.apply {
                if (listItem.id == selectedItem.id) {
                    listItem.selected = selectedItem.selected
                } else {
                    listItem.selected = false
                }
            }
        }

        viewModelScope.launch {

        }
    }

    fun addProductToCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.addProductInCart(item)
//            val uiProducts = updateProducts(false)
//            _uiProducts.update {
//                uiProducts
//            }
            launch { updateCartBill() }
            launch { checkCartEmpty() }
        }
    }

    fun takeProductFromCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.takeProductFromCart(item)
//            val uiProducts = updateProducts(false)
//            _uiProducts.update {
//                uiProducts
//            }
            launch { updateCartBill() }
            launch { checkCartEmpty() }
        }
    }
}