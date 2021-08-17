package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.emikhalets.myfinances.utils.enums.AppIcon
import com.emikhalets.myfinances.utils.formatValue

@Composable
fun AddWalletDialog(
    onSave: (name: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
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
            leadingIcon = AppIcon.Pencil.icon,
            label = stringResource(R.string.name),
            errorEmpty = nameError
        )
        AppTextField(
            value = value,
            onValueChange = {
                value = it.formatValue()
                valueError = false
            },
            leadingIcon = AppIcon.Money.icon,
            label = stringResource(R.string.value),
            errorInvalid = valueError,
            type = KeyboardType.Number,
            visualTransformation = CurrencyTransformation()
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                val amount = try {
                    value.toDouble()
                } catch (ex: NumberFormatException) {
                    ex.printStackTrace()
                    valueError = true
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