package com.emikhalets.myfinances.presentation.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.copyOrNew
import com.emikhalets.myfinances.presentation.core.AppDialog
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.core.TransactionTypeChooser
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun CategoryDialog(
    category: Category?,
    onDismiss: () -> Unit,
    onSaveClick: (Category) -> Unit,
    onDeleteClick: (Category) -> Unit,
) {
    val isEditCategory by remember { mutableStateOf(category != null) }

    var name by remember { mutableStateOf(category?.name ?: "") }
    var type by remember { mutableStateOf(category?.type ?: TransactionType.Expense) }

    var nameEmptyError by remember { mutableStateOf(false) }

    AppDialog(
        label = stringResource(R.string.dialog_label_new_category),
        onDismiss = { onDismiss() }
    ) {
        DialogLayout(
            name = name,
            type = type,
            isEditCategory = isEditCategory,
            nameEmptyError = nameEmptyError,
            onNameChange = {
                name = it
                nameEmptyError = false
            },
            onTypeSelect = { type = it },
            onSaveClick = {
                if (name.trim().isEmpty()) {
                    nameEmptyError = true
                } else {
                    onSaveClick(category.copyOrNew(name, type))
                    onDismiss()
                }
            },
            onDeleteClick = {
                category?.let(onDeleteClick)
                onDismiss()
            }
        )
    }
}

@Composable
private fun DialogLayout(
    name: String,
    type: TransactionType,
    isEditCategory: Boolean,
    nameEmptyError: Boolean,
    onNameChange: (String) -> Unit,
    onTypeSelect: (TransactionType) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        if (!isEditCategory) {
            TransactionTypeChooser(type, onTypeSelect)
            Spacer(Modifier.height(16.dp))
        }
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            label = stringResource(R.string.label_name),
            error = if (nameEmptyError) stringResource(R.string.error_empty_field) else null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))
        Row(Modifier.fillMaxWidth()) {
            if (isEditCategory) {
                AppTextButton(
                    text = stringResource(R.string.app_delete),
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f)
                )
            }
            AppTextButton(
                text = stringResource(R.string.app_save),
                onClick = onSaveClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        DialogLayout(
            name = "Some name",
            type = TransactionType.Expense,
            isEditCategory = true,
            nameEmptyError = false,
            onNameChange = {},
            onTypeSelect = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogAddingPreview() {
    AppTheme {
        DialogLayout(
            name = "Some name",
            type = TransactionType.Income,
            isEditCategory = false,
            nameEmptyError = true,
            onNameChange = {},
            onTypeSelect = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}