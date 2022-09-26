package com.emikhalets.myfinances.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.presentation.theme.appBackground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppBaseDialog(
    onDismiss: () -> Unit,
    label: String = "",
    padding: Dp = 16.dp,
    cancelable: Boolean = false,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = !cancelable,
            dismissOnClickOutside = !cancelable,
            usePlatformDefaultWidth = false
        ),
    ) {
        DialogLayout(label, padding, content)
    }
}

@Composable
private fun DialogLayout(label: String, padding: Dp, content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(40.dp)
            .background(
                color = MaterialTheme.colors.appBackground,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(Modifier.padding(padding)) {
            if (label.isNotEmpty()) {
                TextPrimaryFillWidth(
                    text = label,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            content()
        }
    }
}

@Preview
@Composable
private fun DialogPreview() {
    AppTheme {
        DialogLayout(
            label = "Preview label",
            padding = 16.dp
        ) {
            TextPrimary("Preview dialog text")
        }
    }
}