package com.max.foodies.screens.cartScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.max.foodies.screens.cartScreen.composeItems.CartProductsList

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.Factory),
    onNavigateToProduct: (id: Int?) -> Unit,
) {
    val uiCartProducts = viewModel.uiCartProducts.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
//        TopAppBar(
//
//            onBackPressed = { onBackPressed() },
//            modifier = modifier
//                .height(72.dp)
//                .clip(GenericShape { size, _ ->
//                    lineTo(size.width, 0f)
//                    lineTo(size.width, Float.MAX_VALUE)
//                    lineTo(0f, Float.MAX_VALUE)
//                })
//                .shadow(elevation = 4.dp)
//                .background(color = MaterialTheme.colorScheme.background)
//        )

        CartProductsList(
            modifier = modifier,
            products = uiCartProducts.value,
            onNavigateToProduct = onNavigateToProduct
        )
    }
}


@Preview
@Composable
fun CartScreenPreview() {
    CartScreen(onBackPressed = {}, onNavigateToProduct = {})
}