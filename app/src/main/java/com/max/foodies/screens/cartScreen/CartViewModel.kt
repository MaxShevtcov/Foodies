package com.max.foodies.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.max.foodies.data.repositories.CartRepositoryImpl
import com.max.foodies.data.room.roomDatabase.FoodiesDatabase
import com.max.foodies.screens.UiCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepositoryImpl: CartRepositoryImpl
) : ViewModel() {

    private val _uiCartProducts: MutableStateFlow<List<UiCartProduct>> =
        MutableStateFlow(emptyList())
    val uiCartProducts: StateFlow<List<UiCartProduct>> = _uiCartProducts.asStateFlow()

    init {
        viewModelScope.launch {
            val cartProducts = updateCartProducts()
            _uiCartProducts.update {
                cartProducts
            }
        }
    }

    private suspend fun updateCartProducts(): List<UiCartProduct> {
        return cartRepositoryImpl.getProductAndCountInCart()
    }
}