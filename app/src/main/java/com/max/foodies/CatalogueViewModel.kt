package com.max.foodies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.network.FoodiesApi
import com.max.foodies.network.pojo.Category
import com.max.foodies.room.CartDatabase
import com.max.foodies.screens.catalogue.CatalogueState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogueViewModel(
    private val catalogueRepository: CatalogueRepository,
) : ViewModel() {

    private val _catalogueState: MutableStateFlow<CatalogueState> =
        MutableStateFlow(CatalogueState())
    val catalogueState: StateFlow<CatalogueState> = _catalogueState.asStateFlow()



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
            val product = catalogueRepository.getProducts()
            val filteredProducts =
                product.asSequence().filter { product -> product.categoryId == category.id }

            var concatenatedFilteredProducts = _catalogueState.value.filteredProducts.toMutableList()

            if (selected) {
                concatenatedFilteredProducts.addAll(filteredProducts)
            } else {
                concatenatedFilteredProducts.removeAll(filteredProducts)
            }

            _catalogueState.update {
                it.copy(
                    products = if (concatenatedFilteredProducts.isEmpty()) product else concatenatedFilteredProducts,
                    filteredProducts = concatenatedFilteredProducts
                )
            }
        }
    }

    fun selectCategory(item: Category, selected: Boolean) {
        _catalogueState.value.categories.find { it.id == item.id }?.let { category ->
            category.selected = selected
            filterProducts(category, selected)
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
//        fun createFactory(context: Context): ViewModelProvider.Factory =
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(
//                    modelClass: Class<T>,
//                    extras: CreationExtras
//                ): T {
//                    if (modelClass.isAssignableFrom(CatalogueViewModel::class.java)) {
//                        val catalogueRepository = CatalogueRepository(
//                            localDataSource = CartDatabase.getInstance(context).cartDao(),
//                            networkDataSource = FoodiesApi.foodiesApiService
//                        )
//                        return CatalogueViewModel(
//                            catalogueRepository,
//                        ) as T
//                    }
//                    throw IllegalArgumentException("unknown ViewModel")
//                }
//            }
    }
}