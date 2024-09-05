package com.max.foodies.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.max.foodies.R

@Composable
fun ComposeIcon(
    resourceId: Int,
    annotation: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    Icon(
        painter = painterResource(id = resourceId),
        contentDescription = annotation,
        modifier = modifier,
        tint = tint
    )
}

object ComposeIcons {
    val filter = R.drawable.filter
    val search = R.drawable.search
    val logo = R.drawable.ic_logo
    val arrowLeft = R.drawable.arrowleft
}