package com.max.foodies.screens.catalogue


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.max.foodies.CatalogueViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CatalogueScreen(
    catalogueViewModel: CatalogueViewModel = viewModel(factory = CatalogueViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val catalogueScreenState = catalogueViewModel.catalogueState.collectAsState()
    Column {
        CatalogueTopBar(modifier = modifier.height(64.dp))
        ProductsList(modifier = modifier, products = catalogueScreenState.value.product)
    }

}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun CatalogueScreenPreview() {
    CatalogueScreen()
}



