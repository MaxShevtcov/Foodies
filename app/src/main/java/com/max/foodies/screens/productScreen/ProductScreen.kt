package com.max.foodies.screens.productScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.max.foodies.screens.UiProduct
import com.max.foodies.utils.priceConverterUtil

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    id: Int,
    onBackPressed: () -> Unit,
    productViewModel: ProductViewModel = viewModel(
        factory =
        ProductViewModel.Factory
    )
) {

     val uiProducts = productViewModel.uiProducts.collectAsState()

    Column(modifier.padding(16.dp)) {
        GlideImage(
            model = "https://i.ibb.co/Kwk25pz/Photo.png",
            contentDescription = "Image of Food"
        )
        Text("${uiProducts.value.getOrNull(id)?.name}", modifier.weight(1f))
        Text("${uiProducts.value.getOrNull(id)?.description} ")
        ElevatedButton(
            onClick = { /*TODO*/ },
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        ) {
            Text(text = "В корзину за ${priceConverterUtil(uiProducts.value.getOrNull(id)?.priceCurrent)} P")
        }
    }
}


//@Preview(showBackground = true, widthDp = 160)
//@Composable
//fun ProductScreenPreview() {
//    ProductScreen(modifier = Modifier, productId = 1, onBackPressed = {})
//}