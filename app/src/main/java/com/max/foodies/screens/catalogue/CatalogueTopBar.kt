package com.max.foodies.screens.catalogue

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

import com.max.foodies.ui.theme.ComposeIcon
import com.max.foodies.ui.theme.ComposeIcons
import com.max.foodies.ui.theme.Orange

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatalogueTopBar(modifier: Modifier) {
    Row() {
        IconButton(
            modifier = modifier.padding(8.dp),
            onClick = { /*TODO*/ }
        ) {
            ComposeIcon(resourceId = ComposeIcons.filter, annotation = "filter")
        }

        ComposeIcon(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight(),
            resourceId = ComposeIcons.logo,
            annotation = "logo",
            tint = Orange
        )

        IconButton(
            modifier = modifier.padding(8.dp),
            onClick = { /*TODO*/ }
        ) {
            ComposeIcon(resourceId = ComposeIcons.search, annotation = "search")
        }

    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 64)
@Composable
fun CatalogueTopBarPreview() {
    CatalogueTopBar(Modifier)
}