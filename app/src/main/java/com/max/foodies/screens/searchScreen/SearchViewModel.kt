package com.max.foodies.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.repositories.ProductsRepository
import com.max.foodies.data.use_cases.CartUseCase
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
class SearchViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val cartUseCase: CartUseCase
) : ViewModel() {

    private val _uiProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())
    val uiProducts: StateFlow<List<UiProduct>> = _uiProducts.asStateFlow()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _isSearching: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val searchedProducts: MutableStateFlow<List<UiProduct>> = MutableStateFlow(emptyList())

    private suspend fun updateProducts(forceUpdate: Boolean): List<UiProduct> {
        return productsRepository.getProducts(forceUpdate)
    }

    private fun filterSearchedProducts() {
        viewModelScope.launch(Dispatchers.Default) {
            val products = updateProducts(false)
            searchedProducts.value = products.filter { product ->
                product.name?.uppercase()?.contains(
                    _searchText.value.trim().uppercase()
                )
                    ?: false
            }
            _uiProducts.update {
                searchedProducts.value
            }
        }
    }

    fun onSearchTextChange(text: String) {
        filterSearchedProducts()
        _searchText.update {
            text
        }
    }

    fun onToogleSearch() {
        val isSearching = _isSearching.value
        _isSearching.update {
            !isSearching
        }
    }

    fun addProductToCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.addProductInCart(item)
        }
    }

    fun takeProductFromCart(item: UiProduct) {
        viewModelScope.launch {
            cartUseCase.takeProductFromCart(item)
        }
    }


}