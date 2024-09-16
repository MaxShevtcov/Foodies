package com.max.foodies.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.model.ProductsRepository
import com.max.foodies.model.mappers.toProduct
import com.max.foodies.model.network.Retrofit
import com.max.foodies.model.network.pojo.Product
import com.max.foodies.model.room.catalogueDatabase.CatalogueDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _searchUiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    private val searchedProducts: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())

    fun onSearchTextChange(text: String) {
        filterSearchedProducts()
        _searchUiState.update {
            it.copy(
                searchText = text,
            )
        }
    }

    fun onToogleSearch() {
        val isSearching = _searchUiState.value.isSearching
        _searchUiState.update {
            it.copy(
                isSearching = !isSearching
            )
        }
    }

    private fun filterSearchedProducts() {
        viewModelScope.launch(Dispatchers.Default) {
            val products = productsRepository.getProductsFormDb().map { it.toProduct() }
            searchedProducts.value = products.filter { product ->
                product.name?.uppercase()?.contains(
                    _searchUiState.value.searchText.trim().uppercase()
                )
                    ?: false
            }
            _searchUiState.update {
                it.copy(
                    products = searchedProducts.value
                )
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
                            localDataSource = CatalogueDatabase.getInstance(
                                application.applicationContext,
                                applicationScope
                            )
                                .catalogueDao(),
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