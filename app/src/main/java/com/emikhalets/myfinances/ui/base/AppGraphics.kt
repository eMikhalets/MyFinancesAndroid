package com.emikhalets.myfinances.ui.base

import androidx.annotation.DrawableRes
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

@Composable
fun AppIcon(
    @DrawableRes drawable: Int?,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MaterialTheme.colors.onPrimary
) {
    if (drawable == null) return

    Icon(
        painter = painterResource(drawable),
        contentDescription = "",
        tint = color,
        modifier = if (size > 0.dp) {
            modifier.size(size)
        } else {
            modifier
        }
    )
}

@Composable
fun AppImage(
    @DrawableRes drawable: Int?,
    modifier: Modifier = Modifier,
    size: Dp = 0.dp
) {
    if (drawable == null) return

    Image(
        painter = painterResource(drawable),
        contentDescription = "",
        modifier = if (size > 0.dp) {
            modifier.size(size)
        } else {
            modifier
        }
    )
}