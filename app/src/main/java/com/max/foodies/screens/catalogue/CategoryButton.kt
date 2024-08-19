package com.max.foodies.screens.catalogue

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.max.foodies.network.pojo.Category
import com.max.foodies.ui.theme.Orange

@Composable
fun CategoryButton(
    modifier: Modifier,
    category: Category,
    onSelected: (Category,Boolean) -> Unit,
    selected: Boolean
) {
    if (selected) {
        ElevatedButton(
            onClick = {onSelected(category,!selected)} ,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(15),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            colors = ButtonDefaults.buttonColors(Orange)
        ) {
            Text(text = "${category.name}")
        }
    }else {
        ElevatedButton(
            onClick = {onSelected(category,!selected)},
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(15),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        ) {
            Text(text = "${category.name}")
        }
    }


}
