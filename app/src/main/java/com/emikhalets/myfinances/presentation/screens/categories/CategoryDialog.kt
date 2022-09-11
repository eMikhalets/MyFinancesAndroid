package com.emikhalets.myfinances.presentation.screens.categories

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.copyOrNew
import com.emikhalets.myfinances.presentation.core.AppBaseDialog
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun CategoryDialog(
    category: Category?,
    onDismiss: () -> Unit,
    onSaveClick: (Category) -> Unit,
    onDeleteClick: (Category) -> Unit,
) {
    val isEdit by remember { mutableStateOf(category != null) }

    var name by remember { mutableStateOf(category?.name ?: "") }
    var type by remember { mutableStateOf(category?.type ?: TransactionType.Expense) }

    AppBaseDialog(
        label = stringResource(R.string.dialog_label_new_category),
        onDismiss = { onDismiss() },
        padding = 8.dp
    ) {
        DialogLayout(
            name = name,
            type = type,
            isEdit = isEdit,
            onNameChange = { name = it },
            onTypeSelect = { type = it },
            onSaveClick = { onSaveClick(category.copyOrNew(name, type)) },
            onDeleteClick = { category?.let(onDeleteClick) }
        )
    }
}

@Composable
private fun DialogLayout(
    name: String,
    type: TransactionType,
    isEdit: Boolean,
    onNameChange: (String) -> Unit,
    onTypeSelect: (TransactionType) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        if (!isEdit) {
            TransactionTypeChooser(type, onTypeSelect)
            Spacer(Modifier.height(16.dp))
        }
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            labelRes = R.string.label_name
        )
        Spacer(Modifier.height(16.dp))
        ControlButtons(isEdit, onSaveClick, onDeleteClick)
    }
}

@Composable
private fun TransactionTypeChooser(
    type: TransactionType,
    onTypeSelect: (TransactionType) -> Unit,
) {
    Row(Modifier.fillMaxWidth()) {
        AppText(
            text = stringResource(R.string.expenses),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onTypeSelect(TransactionType.Expense) }
                .borderTypeExpense(type)
                .padding(8.dp)
        )
        AppText(
            text = stringResource(R.string.incomes),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onTypeSelect(TransactionType.Income) }
                .borderTypeIncome(type)
                .padding(8.dp)
        )
    }
}

@Composable
private fun Modifier.borderTypeExpense(type: TransactionType) = when (type) {
    TransactionType.Expense -> border(1.dp, Color.Black)
    else -> border(0.dp, MaterialTheme.colors.surface)
}

@Composable
private fun Modifier.borderTypeIncome(type: TransactionType) = when (type) {
    TransactionType.Income -> border(1.dp, Color.Black)
    else -> border(0.dp, MaterialTheme.colors.surface)
}

@Composable
private fun ControlButtons(
    showDelete: Boolean,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(Modifier.fillMaxWidth()) {
        if (showDelete) {
            AppTextButton(
                text = stringResource(R.string.delete),
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f)
            )
        }
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
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
                type = TransactionType.Expense,
                isEdit = true,
                onNameChange = {},
                onTypeSelect = {},
                onSaveClick = {},
                onDeleteClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogAddingPreview() {
    MyFinancesTheme {
        AppBaseDialog(
            label = "Preview label",
            onDismiss = {},
            padding = 8.dp
        ) {
            DialogLayout(
                name = "Some name",
                type = TransactionType.Income,
                isEdit = false,
                onNameChange = {},
                onTypeSelect = {},
                onSaveClick = {},
                onDeleteClick = {}
            )
        }
    }
}