package com.max.foodies.screens.cartScreen.composeItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.max.foodies.screens.UiCartProduct
import com.max.foodies.screens.UiProduct
import com.max.foodies.utils.priceConverterUtil

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartProductCard(modifier: Modifier, product: UiCartProduct) {
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
                Text("${product.product.name}", modifier)
                Row(modifier = modifier) {
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        modifier = modifier,
                        shape = RoundedCornerShape(15),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    ) {
                        Text(text = "-")
                    }
                    Text(textAlign = TextAlign.Center, text = "${product.cartCounter}") //добавить связи в БД (новая таблица)
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        modifier = modifier,
                        shape = RoundedCornerShape(15),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    ) {
                        Text(text = "+")
                    }
                    Spacer(modifier = modifier.weight(1f))
                    Text(text = "${priceConverterUtil(product.product.priceCurrent)} Р")  //имплементировать product
                }
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 160)
@Composable
fun CartProductCardPreview() {
    CartProductCard(modifier = Modifier, product = UiCartProduct(UiProduct(), 0))
}