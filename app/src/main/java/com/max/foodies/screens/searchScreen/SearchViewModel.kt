package com.max.foodies.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.data.repositories.ProductsRepositoryImpl
import com.max.foodies.data.network.Retrofit
import com.max.foodies.data.room.roomDatabase.FoodiesDatabase
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val productsRepositoryImpl: ProductsRepositoryImpl
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
        return productsRepositoryImpl.getProducts(forceUpdate)
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
}