package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.utils.enums.MyIcons

@Composable
fun ButtonAdd(
    text: String,
    onClick: () -> Unit
) {
    TextWithIcon(
        text = text,
        icon = MyIcons.Plus.icon,
        onClick = onClick
    )
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colors.primary,
    padding: PaddingValues = PaddingValues(8.dp)
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth()
    ) {
        AppText(
            text = text,
            color = textColor,
            modifier = Modifier.padding(padding)
        )
    }
}