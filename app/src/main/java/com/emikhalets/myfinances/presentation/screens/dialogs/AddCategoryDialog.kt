package com.emikhalets.myfinances.presentation.screens.dialogs

import AppBaseDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme

@Composable
fun AddCategoryDialog(
    onSave: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    AppBaseDialog(
        label = stringResource(R.string.dialog_label_new_category),
        onDismiss = { onDismiss() },
        padding = 8.dp
    ) {
        DialogLayout(
            name = name,
            nameError = nameError,
            onNameChange = { name = it },
            onNameErrorChange = { nameError = it },
            onSaveClick = { onSave(name) }
        )
    }
}

@Composable
private fun DialogLayout(
    name: String,
    nameError: Boolean,
    onNameChange: (String) -> Unit,
    onNameErrorChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        AppTextField(
            value = name,
            onValueChange = {
                onNameChange(it)
                onNameErrorChange(false)
            },
            labelRes = R.string.label_name,
            errorEmpty = nameError
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                when {
                    name.isEmpty() -> onNameErrorChange(true)
                    else -> onSaveClick()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    MyFinancesTheme {
        AppBaseDialog(
            label = "Preview label",
            onDismiss = {},
            padding = 8.dp
        ) {
            DialogLayout(
                name = "Some name",
                nameError = false,
                onNameChange = {},
                onNameErrorChange = {},
                onSaveClick = {},
            )
        }
    }
}