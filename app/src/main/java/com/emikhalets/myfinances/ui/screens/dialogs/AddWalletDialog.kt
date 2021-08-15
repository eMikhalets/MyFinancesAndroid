package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppDialogCustom
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextField

@Composable
fun AddWalletDialog(
    onSave: (name: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    AppDialogCustom(
        label = stringResource(R.string.name),
        onDismiss = { onDismiss() }
    ) {
        AppTextField(
            placeholder = stringResource(R.string.name),
            value = name,
            errorEmpty = nameError,
            onValueChange = {
                name = it
                nameError = false
            }
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                if (name.isNotEmpty()) onSave(name, 0.0)
                else nameError = true
            }
        )
    }
}