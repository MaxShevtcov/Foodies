package com.max.foodies.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.data.ProductsRepository
import com.max.foodies.data.network.Retrofit
import com.max.foodies.data.network.pojo.Product
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

class SearchViewModel(
    private val productsRepository: ProductsRepository
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
        return productsRepository.invoke(forceUpdate)
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

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                        val application = checkNotNull(extras[APPLICATION_KEY])
                        val applicationScope = CoroutineScope(SupervisorJob())
                        val productsRepository = ProductsRepository(
                            localDataSource = FoodiesDatabase.getInstance(
                                application.applicationContext,
                                applicationScope
                            )
                                .productDao(),
                            networkDataSource = Retrofit.productsApi
                        )
                        return SearchViewModel(
                            productsRepository = productsRepository
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }
}