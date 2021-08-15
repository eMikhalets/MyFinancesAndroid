package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppDialogCustom
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextField
import com.emikhalets.myfinances.utils.CurrencyTransformation

@Composable
fun AddWalletDialog(
    onSave: (name: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf(0.0) }
    var nameError by remember { mutableStateOf(false) }
    var valueError by remember { mutableStateOf(false) }

    AppDialogCustom(
        label = stringResource(R.string.new_wallet),
        onDismiss = { onDismiss() }
    ) {
        AppTextField(
            value = name,
            onValueChange = {
                nameError = false
                name = it
            },
            leadingIcon = Icons.Default.Edit,
            label = stringResource(R.string.name),
            errorEmpty = nameError
        )
        AppTextField(
            value = formatValue(value),
            onValueChange = {
                try {
                    value = it.toDouble()
                    valueError = false
                } catch (ex: NumberFormatException) {
                    ex.printStackTrace()
                }
            },
            leadingIcon = Icons.Default.Edit,
            label = stringResource(R.string.value_number),
            errorInvalid = valueError,
            type = KeyboardType.Number,
            visualTransformation = CurrencyTransformation()
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                when {
                    name.isEmpty() -> nameError = true
                    else -> onSave(name, value)
                }
            }
        )
    }
}

private fun formatValue(value: Double): String {
    return if (value == 0.0) ""
    else "$value"
}