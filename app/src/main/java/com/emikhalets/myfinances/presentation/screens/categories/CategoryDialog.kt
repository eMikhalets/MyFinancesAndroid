//package com.emikhalets.myfinances.presentation.screens.categories
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.emikhalets.myfinances.R
//import com.emikhalets.myfinances.domain.entity.Category
//import com.emikhalets.myfinances.domain.entity.copyOrNew
//import com.emikhalets.myfinances.presentation.core.AppBaseDialog
//import com.emikhalets.myfinances.presentation.core.AppTextButton
//import com.emikhalets.myfinances.presentation.core.AppTextField
//import com.emikhalets.myfinances.presentation.core.TransactionTypeChooser
//import com.emikhalets.myfinances.presentation.theme.AppTheme
//import com.emikhalets.myfinances.utils.enums.TransactionType
//
//@Composable
//fun CategoryDialog(
//    category: Category?,
//    onDismiss: () -> Unit,
//    onSaveClick: (Category) -> Unit,
//    onDeleteClick: (Category) -> Unit,
//) {
//    val isEdit by remember { mutableStateOf(category != null) }
//
//    var name by remember { mutableStateOf(category?.name ?: "") }
//    var type by remember { mutableStateOf(category?.type ?: TransactionType.Expense) }
//
//    var nameEmpty by remember { mutableStateOf(false) }
//
//    AppBaseDialog(
//        label = stringResource(R.string.dialog_label_new_category),
//        onDismiss = { onDismiss() }
//    ) {
//        DialogLayout(
//            name = name,
//            type = type,
//            isEdit = isEdit,
//            nameEmpty = nameEmpty,
//            onNameChange = {
//                name = it
//                nameEmpty = false
//            },
//            onTypeSelect = { type = it },
//            onSaveClick = {
//                if (name.isEmpty() || name.isBlank()) {
//                    nameEmpty = true
//                } else {
//                    onSaveClick(category.copyOrNew(name, type))
//                    onDismiss()
//                }
//            },
//            onDeleteClick = {
//                category?.let(onDeleteClick)
//                onDismiss()
//            }
//        )
//    }
//}
//
//@Composable
//private fun DialogLayout(
//    name: String,
//    type: TransactionType,
//    isEdit: Boolean,
//    nameEmpty: Boolean,
//    onNameChange: (String) -> Unit,
//    onTypeSelect: (TransactionType) -> Unit,
//    onSaveClick: () -> Unit,
//    onDeleteClick: () -> Unit,
//) {
//    Column(Modifier.fillMaxWidth()) {
//        if (!isEdit) {
//            TransactionTypeChooser(type, onTypeSelect)
//            Spacer(Modifier.height(16.dp))
//        }
//        AppTextField(
//            value = name,
//            onValueChange = onNameChange,
//            label = stringResource(R.string.label_name),
//            error = if (nameEmpty) stringResource(R.string.error_empty_field) else null,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(32.dp))
//        ControlButtons(isEdit, onSaveClick, onDeleteClick)
//    }
//}
//
//@Composable
//private fun ControlButtons(
//    showDelete: Boolean,
//    onSaveClick: () -> Unit,
//    onDeleteClick: () -> Unit,
//) {
//    Row(Modifier.fillMaxWidth()) {
//        if (showDelete) {
//            AppTextButton(
//                text = stringResource(R.string.delete),
//                onClick = onDeleteClick,
//                modifier = Modifier.weight(1f)
//            )
//        }
//        AppTextButton(
//            text = stringResource(R.string.save),
//            onClick = onSaveClick,
//            modifier = Modifier.weight(1f)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun DialogPreview() {
//    AppTheme {
//        AppBaseDialog(
//            label = "Preview label",
//            onDismiss = {}
//        ) {
//            DialogLayout(
//                name = "Some name",
//                type = TransactionType.Expense,
//                isEdit = true,
//                nameEmpty = false,
//                onNameChange = {},
//                onTypeSelect = {},
//                onSaveClick = {},
//                onDeleteClick = {}
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun DialogAddingPreview() {
//    AppTheme {
//        AppBaseDialog(
//            label = "Preview label",
//            onDismiss = {}
//        ) {
//            DialogLayout(
//                name = "Some name",
//                type = TransactionType.Income,
//                isEdit = false,
//                nameEmpty = true,
//                onNameChange = {},
//                onTypeSelect = {},
//                onSaveClick = {},
//                onDeleteClick = {}
//            )
//        }
//    }
//}