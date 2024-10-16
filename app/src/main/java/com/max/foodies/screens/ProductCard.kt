package com.max.foodies.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.max.foodies.utils.priceConverterUtil

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductCard(
    modifier: Modifier,
    product: UiProduct,
    onNavigateToProduct: (id: Int?) -> Unit,
    onAddProductToCart: (uiProduct: UiProduct) -> Unit
) {
    Card(
        modifier
            .padding(8.dp)
            .height(320.dp)
            .fillMaxWidth(),
        shape = CardDefaults.elevatedShape,
    ) {
        Column(modifier.padding(16.dp)) {
            GlideImage(
                model = "https://i.ibb.co/Kwk25pz/Photo.png",
                contentDescription = "Image of Food"
            )
            Text("${product.name}", modifier.weight(1f))
            Text("${product.measure} ${product.measureUnit}")
            ElevatedButton(
                onClick = {
                    onAddProductToCart(product)
                },
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            ) {
                Text(text = "${priceConverterUtil(product?.priceCurrent)} P")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 160)
@Composable
fun ProductCardPreview() {
    ProductCard(
        modifier = Modifier,
        product = UiProduct(),
        onNavigateToProduct = {},
        onAddProductToCart = {}
    )
}