package com.max.foodies.screens.productScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.foodies.data.repositories.ProductsRepository
import com.max.foodies.screens.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
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
}