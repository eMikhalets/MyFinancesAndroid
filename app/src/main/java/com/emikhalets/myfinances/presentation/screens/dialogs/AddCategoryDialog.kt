package com.emikhalets.myfinances.presentation.screens.dialogs

import AppDialogCustom
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.base.AppTextButton
import com.emikhalets.myfinances.presentation.base.NameTextField

@Composable
fun AddCategoryDialog(
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    AppDialogCustom(
        label = stringResource(R.string.new_category),
        onDismiss = { onDismiss() },
        padding = 8.dp
    ) {
        NameTextField(
            name = name,
            error = nameError,
            onNameChange = {
                name = it
                nameError = false
            }
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                when {
                    name.isEmpty() -> nameError = true
                    else -> onSave(name)
                }
            }
        )
    }
}