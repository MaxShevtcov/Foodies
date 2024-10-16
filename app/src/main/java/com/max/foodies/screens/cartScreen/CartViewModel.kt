package com.max.foodies.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.data.CartRepository
import com.max.foodies.data.CategoryRepository
import com.max.foodies.data.ProductsRepository
import com.max.foodies.data.network.Retrofit
import com.max.foodies.data.room.roomDatabase.FoodiesDatabase
import com.max.foodies.screens.UiCartProduct
import com.max.foodies.screens.catalogueScreen.CatalogueViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiCartProducts: MutableStateFlow<List<UiCartProduct>> =
        MutableStateFlow(emptyList())
    val uiCartProducts: StateFlow<List<UiCartProduct>> = _uiCartProducts.asStateFlow()

    init {
        viewModelScope.launch {
            _uiCartProducts.update {
                updateCartProducts()
            }
        }

    }

    private suspend fun updateCartProducts():List<UiCartProduct> {
       return cartRepository.getProductAndCountInCart()
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                        val application = checkNotNull(extras[APPLICATION_KEY])
                        val applicationScope = CoroutineScope(SupervisorJob())
                        val cartRepository = CartRepository(
                            localCartDataSource = FoodiesDatabase.getInstance(
                                application.applicationContext,
                                applicationScope
                            )
                                .cartDao(),
                        )
                        return CartViewModel(
                            cartRepository = cartRepository,
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }
}