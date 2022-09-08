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
import com.emikhalets.myfinances.presentation.base.ValueTextField
import com.emikhalets.myfinances.utils.formatValue

@Composable
fun AddWalletDialog(
    onSave: (name: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    AddWalletDialog(
        name = name,
        value = value,
        nameError = nameError,
        onNameChange = { name = it },
        onValueChange = { value = it },
        onNameErrorChange = { nameError = it },
        onSave = onSave,
        onDismiss = onDismiss
    )
}

@Composable
private fun AddWalletDialog(
    name: String,
    value: String,
    nameError: Boolean,
    onNameChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onNameErrorChange: (Boolean) -> Unit,
    onSave: (name: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    AppDialogCustom(
        label = stringResource(R.string.new_wallet),
        onDismiss = { onDismiss() }
    ) {
        NameTextField(
            name = name,
            error = nameError,
            onNameChange = {
                onNameChange(it)
                onNameErrorChange(false)
            }
        )
        ValueTextField(
            value = value,
            error = false,
            onValueChange = { onValueChange(it.formatValue()) }
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                val amount = try {
                    value.toDouble()
                } catch (ex: NumberFormatException) {
                    ex.printStackTrace()
                    0.0
                }
                when {
                    name.isEmpty() -> onNameErrorChange(true)
                    else -> onSave(name, amount)
                }
            }
        )
    }
}