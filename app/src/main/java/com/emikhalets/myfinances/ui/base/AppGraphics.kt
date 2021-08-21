package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.utils.enums.MyIcons

@Composable
fun AppIcon(
    icon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MaterialTheme.colors.onPrimary
) {
    if (icon == MyIcons.App.icon) {
        Image(painter = painterResource(MyIcons.App.icon), contentDescription = "")
    } else {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = color,
            modifier = modifier.size(size)
        )
    }
}