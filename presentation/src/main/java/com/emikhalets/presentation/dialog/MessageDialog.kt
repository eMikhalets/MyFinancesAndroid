package com.emikhalets.presentation.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.presentation.R
import com.emikhalets.presentation.core.AppButton
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun MessageDialog(
    message: String,
    onDismiss: () -> Unit,
    title: String = "",
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = if (title.isNotEmpty()) {
            { Text(title) }
        } else null,
        text = { Text(message) },
        confirmButton = {
            AppButton(
                text = stringResource(R.string.dialog_ok),
                onClick = onDismiss
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        MessageDialog(
            message = "Test message dialog",
            onDismiss = {},
            title = "Test title",
        )
    }
}