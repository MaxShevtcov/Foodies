package com.max.foodies.screens.catalogueScreen


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.max.foodies.screens.ProductsList
import com.max.foodies.screens.catalogueScreen.composeItems.CatalogueTopBar
import com.max.foodies.screens.catalogueScreen.composeItems.CategoriesList

@Composable
fun CatalogueScreen(
    catalogueViewModel: CatalogueViewModel = viewModel(factory = CatalogueViewModel.Factory),
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val catalogueScreenState = catalogueViewModel.catalogueState.collectAsState()
    Column {
        CatalogueTopBar(
            onNavigateToSearch =  {onNavigateToSearch()} ,
            modifier = modifier.height(72.dp)
        )

        CategoriesList(
            modifier = modifier,
            categories = catalogueScreenState.value.categories,
            onSelected = { category, selected ->
                catalogueViewModel.selectCategory(
                    category,
                    selected
                )
            }
        )
        ProductsList(modifier = modifier, products = catalogueScreenState.value.products)
    }

}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun CatalogueScreenPreview() {
    CatalogueScreen(onNavigateToSearch = {})
}



