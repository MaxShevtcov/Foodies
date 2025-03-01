package com.max.foodies.screens.cartScreen.composeItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.max.foodies.screens.PlusMinusCartCounter
import com.max.foodies.screens.UiProduct
import com.max.foodies.utils.priceConverterUtil

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartProductCard(
    modifier: Modifier,
    product: UiProduct,
    onAddProductToCart: (uiProduct: UiProduct) -> Unit,
    onTakeProductFromCart: (uiProduct: UiProduct) -> Unit,
) {
    Card(
        modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = CardDefaults.elevatedShape,
    ) {
        Row(modifier.fillMaxWidth()) {
            GlideImage(
                modifier = modifier.size(72.dp),
                model = "https://i.ibb.co/Kwk25pz/Photo.png",
                contentDescription = "Image of Food"
            )
            Column(
                modifier
                    .padding(16.dp)
                    .wrapContentSize()
            ) {
                Text("${product.name}", modifier)
                PlusMinusCartCounter(modifier, product, onAddProductToCart, onTakeProductFromCart)
                Spacer(modifier = modifier.weight(1f))
                Text(text = "${priceConverterUtil(product.priceCurrent)} Р")  //имплементировать product
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 160)
@Composable
fun CartProductCardPreview() {
    CartProductCard(
        modifier = Modifier,
        product = UiProduct(),
        onAddProductToCart = {},
        onTakeProductFromCart = {}
    )
}