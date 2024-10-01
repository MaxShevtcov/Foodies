package com.max.foodies.screens.productScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.NavRoute
import com.max.foodies.data.ProductsRepository
import com.max.foodies.data.network.Retrofit
import com.max.foodies.data.room.roomDatabase.FoodiesDatabase
import com.max.foodies.screens.UiProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ProductViewModel(
    private val productsRepository: ProductsRepository,
) : ViewModel() {





    fun getProductById(id: Int): StateFlow<UiProduct> = flow {
        val product = productsRepository.getProductById(id)
        emit(product)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = UiProduct()
    )



    companion object {
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
                            productsRepository = productsRepository
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }
}