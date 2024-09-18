package com.max.foodies.screens.productScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.toRoute
import com.max.foodies.NavRoute
import com.max.foodies.data.ProductsRepository
import com.max.foodies.data.network.Retrofit
import com.max.foodies.data.room.roomDatabase.FoodiesDatabase
import com.max.foodies.screens.UiCategory
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository,
) : ViewModel() {


    //    private val _uiProduct: MutableStateFlow<UiProduct> = MutableStateFlow(UiProduct())
//    val uiProduct = _uiProduct.asStateFlow()
//
//    private val navProduct = savedStateHandle.toRoute<NavRoute.Product>()
//
//    init{
//        getProduct()
//    }
//
//    private fun getProduct() {
//        viewModelScope.launch {
//            val product: Deferred<UiProduct> = viewModelScope.async {
//                updateProducts(false)[navProduct.id.toInt()]
//            }
//            _uiProduct.update {
//                product.await()
//            }
//        }
//    }
    private val _uiProducts: MutableStateFlow<List<UiProduct>> =
        MutableStateFlow(emptyList())
    val uiProducts: StateFlow<List<UiProduct>> = _uiProducts.asStateFlow()


    init {
        viewModelScope.launch {

            _uiProducts.update {
                updateProducts(false)
            }
        }

    }

    private suspend
    fun updateProducts(forceUpdate: Boolean): List<UiProduct> {
        return productsRepository.invoke(forceUpdate)
    }

    companion
    object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
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

                        return ProductViewModel(
                            savedStateHandle = SavedStateHandle(),
                            productsRepository = productsRepository
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }
}