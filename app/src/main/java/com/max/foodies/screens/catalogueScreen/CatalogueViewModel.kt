package com.max.foodies.screens.catalogueScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.model.CategoryRepository
import com.max.foodies.model.ProductsRepository
import com.max.foodies.model.mappers.toProduct
import com.max.foodies.model.mappers.toProductInCatalogue
import com.max.foodies.model.network.Retrofit
import com.max.foodies.model.network.pojo.Category
import com.max.foodies.model.network.pojo.Product
import com.max.foodies.model.room.catalogueDatabase.CatalogueDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogueViewModel(
    private val productsRepository: ProductsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _catalogueState: MutableStateFlow<CatalogueState> =
        MutableStateFlow(CatalogueState())
    val catalogueState: StateFlow<CatalogueState> = _catalogueState.asStateFlow()

    private val concatenatedFilteredProducts: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())


    init {
        updateCatalogueState()
    }

    private fun insertProducts(products:List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.insertAll(products.map { it.toProductInCatalogue() })
        }
    }

    private fun updateCatalogueState() {
        viewModelScope.launch(Dispatchers.Default) {
            val products = productsRepository.getProducts()
            Log.e("!!!", "product: $products")
            val categories = categoryRepository.getCategories()
            Log.e("!!!", "categories: $categories")
            insertProducts(products)
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
            val products = productsRepository.getProductsFormDb().map { it.toProduct() }
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

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    if (modelClass.isAssignableFrom(CatalogueViewModel::class.java)) {
                        val application = checkNotNull(extras[APPLICATION_KEY])
                        val applicationScope = CoroutineScope(SupervisorJob())
                        val productsRepository = ProductsRepository(
                            localDataSource = CatalogueDatabase.getInstance(application.applicationContext, applicationScope)
                                .catalogueDao(),
                            networkDataSource = Retrofit.productsApi
                        )
                        val categoryRepository = CategoryRepository(
                            networkDataSource = Retrofit.categoriesApi
                        )
                        return CatalogueViewModel(
                            productsRepository = productsRepository,
                            categoryRepository = categoryRepository
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }
}