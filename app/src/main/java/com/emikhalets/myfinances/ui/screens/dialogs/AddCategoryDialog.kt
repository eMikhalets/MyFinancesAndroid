package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddCategoryDialog(
    onDismiss: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(Modifier.background(Color.White)) {
            Text("1243")
        }
    }
}