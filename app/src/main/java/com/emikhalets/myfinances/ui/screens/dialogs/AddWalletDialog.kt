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
import com.emikhalets.myfinances.ui.base.NameTextField
import com.emikhalets.myfinances.ui.base.ValueTextField
import com.emikhalets.myfinances.utils.formatValue

@Composable
fun AddWalletDialog(
    onSave: (name: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    AppDialogCustom(
        label = stringResource(R.string.new_wallet),
        onDismiss = { onDismiss() }
    ) {
        NameTextField(
            name = name,
            error = nameError,
            onNameChange = { name = it }
        )
        ValueTextField(
            value = value,
            error = false,
            onValueChange = { value = it.formatValue() }
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
                    name.isEmpty() -> nameError = true
                    else -> onSave(name, amount)
                }
            }
        )
    }
}