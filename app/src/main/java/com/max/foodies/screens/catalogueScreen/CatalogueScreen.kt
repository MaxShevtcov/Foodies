package com.max.foodies.screens.catalogueScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.max.foodies.screens.ProductsList
import com.max.foodies.screens.catalogueScreen.composeItems.CatalogueTopBar
import com.max.foodies.screens.catalogueScreen.composeItems.CategoriesList
import com.max.foodies.utils.priceConverterUtil
import kotlinx.coroutines.launch

@Composable
fun CatalogueScreen(
    catalogueViewModel: CatalogueViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProduct: (id: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiProduct = catalogueViewModel.uiProducts.collectAsState()
    val uiCategories = catalogueViewModel.uiCategories.collectAsState()
    val isCartEmpty = catalogueViewModel.isCartEmpty.collectAsState()
    val cartBillSum = catalogueViewModel.cartBill.collectAsState()


    Column(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.weight(1f)) {
            CatalogueTopBar(
                onNavigateToSearch = { onNavigateToSearch() },
                modifier = modifier.height(72.dp)
            )


            CategoriesList(

                modifier = modifier,
                categories = uiCategories.value,
                onSelected = { category ->
                    catalogueViewModel.onCategorySelected(
                        category
                    )

                }
            )
            ProductsList(
                modifier = modifier,
                products = uiProduct.value,
                onNavigateToProduct = onNavigateToProduct,
                onAddProductToCart = { uiProduct ->
                    catalogueViewModel.addProductToCart(uiProduct)
                },
                onTakeProductFromCart = { uiProduct ->
                    catalogueViewModel.takeProductFromCart(uiProduct)
                }
            )
        }
        if (!isCartEmpty.value) {
            ElevatedButton(
                onClick = { onNavigateToCart() },
                modifier = modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(15),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            ) {
                Text(text = "${priceConverterUtil(cartBillSum.value)} Р")
            }
        }
    }

}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun CatalogueScreenPreview() {
    CatalogueScreen(onNavigateToSearch = {}, onNavigateToProduct = {}, onNavigateToCart = {})
}



