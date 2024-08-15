package com.max.foodies.utils

fun priceConverterUtil(price: Int?):Int {
    return if (price != null) {
        price / 100
    } else {
        0
    }
}