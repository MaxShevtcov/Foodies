package com.max.foodies.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PlusMinusCartCounter(
    modifier: Modifier,
    product: UiProduct,
    onAddProductToCart: (uiProduct: UiProduct) -> Unit,
    onTakeProductFromCart: (uiProduct: UiProduct) -> Unit,
) {
    Row(modifier = modifier) {
        ElevatedButton(
            onClick = { onTakeProductFromCart(product) },
            modifier = modifier,
            shape = RoundedCornerShape(15),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        ) {
            Text(text = "-")
        }
        Text(
            textAlign = TextAlign.Center,
            text = "${product.countInCart}"
        ) //добавить связи в БД (новая таблица)
        ElevatedButton(
            onClick = { onAddProductToCart(product) },
            modifier = modifier,
            shape = RoundedCornerShape(15),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        ) {
            Text(text = "+")
        }
    }
}