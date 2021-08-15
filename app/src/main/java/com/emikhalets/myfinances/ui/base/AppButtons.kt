package com.emikhalets.myfinances.ui.base

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun AppTextButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    color: Color = MaterialTheme.colors.onSurface,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        )
    }
}