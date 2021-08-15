package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppTextField

@Composable
fun AddCategoryDialog(
    onSave: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            AppTextField(
                label = stringResource(R.string.name),
                value = name,
                emptyError = nameError,
                onValueChange = {
                    name = it
                    nameError = false
                },
                modifier = Modifier.padding(16.dp)
            )
            TextButton(
                onClick = {
                    if (name.isNotEmpty()) onSave(name)
                    else nameError = true
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.save),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}