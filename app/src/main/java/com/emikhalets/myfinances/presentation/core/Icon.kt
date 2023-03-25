package com.emikhalets.myfinances.presentation.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emikhalets.presentation.theme.textPrimary

@Deprecated("")
@Composable
fun AppIcon(
    @DrawableRes drawable: Int?,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MaterialTheme.colors.onPrimary,
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
fun AppIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.textPrimary,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = "",
        tint = color,
        modifier = modifier
    )
}

@Composable
fun AppIcon(
    @DrawableRes drawable: Int?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onPrimary,
) {
    if (drawable == null) return

    Icon(
        painter = painterResource(drawable),
        contentDescription = "",
        tint = color,
        modifier = modifier
    )
}