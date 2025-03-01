package com.max.foodies.screens.productScreen.composeItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun productInfoItem(
    modifier: Modifier = Modifier.height(36.dp),
    infoTag: String,
    infoMeasure: Any?,
    infoUnit: String?
) {
    Column(modifier = modifier) {
        Row(modifier = modifier) {
            Text(modifier = modifier.padding(4.dp), text = infoTag)
            Spacer(modifier.weight(1f))
            Text(
                modifier = modifier.padding(4.dp),
                text = "$infoMeasure $infoUnit"
            )
        }
        Spacer(modifier = modifier.height(2.dp))
    }
}