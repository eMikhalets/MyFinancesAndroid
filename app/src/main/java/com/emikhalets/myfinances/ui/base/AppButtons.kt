package com.emikhalets.myfinances.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    padding: PaddingValues = PaddingValues(0.dp)
) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(8.dp)
        )
    }
}