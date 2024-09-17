package com.max.foodies.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


data class UiCategory(
    val id: Int? = null,
    val name: String? = null,
    var initSelected: Boolean = false

) {
    var selected by mutableStateOf(initSelected)
}