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
import dagger.hilt.android.lifecycle.HiltViewModel
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