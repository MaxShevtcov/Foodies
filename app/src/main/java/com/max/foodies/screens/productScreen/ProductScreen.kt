package com.max.foodies.screens.productScreen

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.max.foodies.screens.UiProduct
import com.max.foodies.screens.productScreen.composeItems.productInfoItem
import com.max.foodies.utils.priceConverterUtil
import kotlinx.coroutines.runBlocking

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

    val scrollState = rememberScrollState()
    val product = remember {
        productViewModel.getProductById(id)
    }.collectAsState()
    Column(modifier.fillMaxSize()) {
        Column(
            modifier
                .verticalScroll(state = scrollState)
                .weight(weight = 1f)
        ) {
            GlideImage(
                model = "https://i.ibb.co/Kwk25pz/Photo.png",
                contentDescription = "Image of Food"
            )
            Text(text = product.value.name.toString())
            Text(text = product.value.description.toString())
            productInfoItem(
                infoTag = "Вес",
                infoMeasure = product.value.measure,
                infoUnit = "г"
            )
            productInfoItem(
                infoTag = "Энерг. ценность",
                infoMeasure = product.value.energyPer100Grams,
                infoUnit = "ккал"
            )
            productInfoItem(
                infoTag = "Белки",
                infoMeasure = product.value.proteinsPer100Grams,
                infoUnit = "г"
            )
            productInfoItem(
                infoTag = "Жиры",
                infoMeasure = product.value.fatsPer100Grams,
                infoUnit = "г"
            )
            productInfoItem(
                infoTag = "Углеводы",
                infoMeasure = product.value.carbohydratesPer100Grams,
                infoUnit = "г"
            )
        }
        ElevatedButton(
            onClick = { /*TODO*/ },
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        ) {
            Text(text = "В корзину за ${priceConverterUtil(product.value?.priceCurrent)} P")
        }
    }
}


@Preview(showBackground = true, widthDp = 160)
@Composable
fun ProductScreenPreview() {
    ProductScreen(modifier = Modifier, id = 1, onBackPressed = {})
}