package com.emikhalets.myfinances.ui.screens.dialogs

import AppDialogCustom
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.IconsList
import com.emikhalets.myfinances.ui.base.NameTextField
import com.emikhalets.myfinances.ui.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.enums.MyIcons

@Composable
fun AddCategoryDialog(
    onSave: (String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf(-1) }
    var nameError by remember { mutableStateOf(false) }
    var iconError by remember { mutableStateOf(false) }

    AddCategoryDialog(
        name = name,
        icon = icon,
        nameError = nameError,
        iconError = iconError,
        onNameChange = { name = it },
        onIconChange = { icon = it },
        onNameErrorChange = { nameError = it },
        onIconErrorChange = { iconError = it },
        onSave = onSave,
        onDismiss = onDismiss
    )
}

@Composable
private fun AddCategoryDialog(
    name: String,
    icon: Int,
    nameError: Boolean,
    iconError: Boolean,
    onNameChange: (String) -> Unit,
    onIconChange: (Int) -> Unit,
    onNameErrorChange: (Boolean) -> Unit,
    onIconErrorChange: (Boolean) -> Unit,
    onSave: (String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    AppDialogCustom(
        label = stringResource(R.string.new_category),
        onDismiss = { onDismiss() }
    ) {
        IconsList(
            iconError = iconError,
            onSelectIcon = {
                onIconChange(it)
                onIconErrorChange(false)
            }
        )
        Spacer(Modifier.height(8.dp))
        NameTextField(
            name = name,
            error = nameError,
            onNameChange = {
                onNameChange(it)
                onNameErrorChange(false)
            }
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                when {
                    name.isEmpty() -> onNameErrorChange(true)
                    icon < 0 -> onIconErrorChange(true)
                    else -> onSave(name, icon)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFinancesTheme {
        AddCategoryDialog(
            name = "Some name",
            icon = MyIcons.Train.id,
            nameError = true,
            iconError = false,
            onNameChange = { },
            onIconChange = {},
            onNameErrorChange = {},
            onIconErrorChange = {},
            onSave = { _, _ -> },
            onDismiss = {}
        )
    }
}