package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.boxBackground
import com.emikhalets.presentation.theme.textButton
import com.emikhalets.presentation.theme.textPrimary

@Composable
fun AppButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.boxBackground,
            contentColor = MaterialTheme.colors.textPrimary
        ),
        modifier = modifier
    ) {
        AppText(
            text = text,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colors.textButton,
    padding: PaddingValues = PaddingValues(8.dp),
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        AppText(
            text = text,
            fontColor = textColor,
            modifier = Modifier.padding(padding)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextButtonPreview() {
    AppTheme {
        AppTextButton(
            text = "Some text",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        AppButton(
            text = "Some text",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}