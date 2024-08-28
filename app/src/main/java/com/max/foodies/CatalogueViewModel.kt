package com.max.foodies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.network.FoodiesApi
import com.max.foodies.network.pojo.Category
import com.max.foodies.network.pojo.Product
import com.max.foodies.room.CartDatabase
import com.max.foodies.screens.catalogue.CatalogueState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogueViewModel(
    private val catalogueRepository: CatalogueRepository,
) : ViewModel() {

    private val _catalogueState: MutableStateFlow<CatalogueState> =
        MutableStateFlow(CatalogueState())
    val catalogueState: StateFlow<CatalogueState> = _catalogueState.asStateFlow()

    private val concatenatedFilteredProducts: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    private val searchedProducts: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())

    init {
        updateCatalogueState()
    }

    private fun updateCatalogueState() {
        viewModelScope.launch(Dispatchers.Default) {
            val products = catalogueRepository.getProducts()
            Log.e("!!!", "product: $products")
            val categories = catalogueRepository.getCategories()
            Log.e("!!!", "categories: $categories")
            _catalogueState.update {
                it.copy(
                    products = products,
                    categories = categories,
                )
            }
        }
    }

    private fun filterProducts(category: Category, selected: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            val products = catalogueRepository.getProducts()
            val filteredProducts = products.filter { product -> product.categoryId == category.id }


            if (selected) {
                concatenatedFilteredProducts.value =
                    concatenatedFilteredProducts.value.plus(filteredProducts)
            } else {
                concatenatedFilteredProducts.value =
                    concatenatedFilteredProducts.value.minus(filteredProducts.toSet())
            }

            _catalogueState.update {
                it.copy(products = concatenatedFilteredProducts.value.ifEmpty { products })
            }
        }
    }

    fun selectCategory(item: Category, selected: Boolean) {
        _catalogueState.value.categories.find { it.id == item.id }?.let { category ->
            category.selected = selected
            filterProducts(category, selected)
        }
    }

    fun onSearchTextChange(text: String) {
        filterSearchedProducts()
        _catalogueState.update {
            it.copy(
                searchText = text,
            )
        }
    }

    fun onToogleSearch() {
        val isSearching = _catalogueState.value.isSearching
        _catalogueState.update {
            it.copy(
                isSearching = !isSearching
            )
        }
    }

    private fun filterSearchedProducts() {
        viewModelScope.launch {
            val products = catalogueRepository.getProducts()
            searchedProducts.value = _catalogueState.value.products.filter { product ->
                product.name?.uppercase()?.contains(
                    _catalogueState.value.searchText.trim().uppercase()
                )
                    ?: false
            }
            _catalogueState.update {
                it.copy(
                    products = when {
                        _catalogueState.value.searchText.isNotBlank() -> searchedProducts.value
                        concatenatedFilteredProducts.value.isNotEmpty() -> concatenatedFilteredProducts.value
                        else -> products
                    }
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
                    if (modelClass.isAssignableFrom(CatalogueViewModel::class.java)) {
                        val application = checkNotNull(extras[APPLICATION_KEY])
                        val catalogueRepository = CatalogueRepository(
                            localDataSource = CartDatabase.getInstance(application.applicationContext)
                                .cartDao(),
                            networkDataSource = FoodiesApi.foodiesApiService
                        )
                        return CatalogueViewModel(
                            catalogueRepository = catalogueRepository
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }
}