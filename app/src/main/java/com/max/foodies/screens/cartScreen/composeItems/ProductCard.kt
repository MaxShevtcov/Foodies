package com.max.foodies.screens.cartScreen.composeItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun CartProduct(modifier: Modifier, product: UiProduct) {
    Card(
        modifier
            .padding(8.dp)
            .height(320.dp)
            .fillMaxWidth(),
        shape = CardDefaults.elevatedShape,
    ) {
        Row(modifier.fillMaxWidth()) {
            GlideImage(
                model = "https://i.ibb.co/Kwk25pz/Photo.png",
                contentDescription = "Image of Food"
            )
            Column(modifier.padding(16.dp)) {
                Text("${product.name}", modifier.weight(1f))
                Row {
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        modifier = modifier,
                        shape = RoundedCornerShape(15),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    ) {
                        Text(text = "-")
                    }
                    Text(text = "1"/*{$product.count}*/) //добавить связи в БД (новая таблица)
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        modifier = modifier,
                        shape = RoundedCornerShape(15),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    ) {
                        Text(text = "-")
                    }
                    Text(text = product.price)  //имплементировать product
                }
                Text("${product.measure} ${product.measureUnit}")

            }
        }

    }
}

@Preview(showBackground = true, widthDp = 160)
@Composable
fun ProductCardPreview() {
    ProductCard(modifier = Modifier, product = UiProduct())
}