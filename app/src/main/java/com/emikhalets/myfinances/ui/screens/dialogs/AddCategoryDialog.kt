package com.emikhalets.myfinances.ui.screens.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.ui.base.AppDialogCustom
import com.emikhalets.myfinances.ui.base.AppTextButton
import com.emikhalets.myfinances.ui.base.AppTextField

@Composable
fun AddCategoryDialog(
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    AppDialogCustom(
        label = stringResource(R.string.new_category),
        onDismiss = { onDismiss() }
    ) {
        AppTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            leadingIcon = Icons.Default.Edit,
            label = stringResource(R.string.name),
            errorEmpty = nameError
        )
        Spacer(Modifier.height(16.dp))
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = {
                nameError = false
                if (name.isNotEmpty()) onSave(name)
                else nameError = true
            }
        )
    }
}