package com.emikhalets.myfinances.presentation.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.copyTransactionOrNew
import com.emikhalets.myfinances.presentation.core.AppBaseDialog
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.core.CategoriesDropMenu
import com.emikhalets.myfinances.presentation.core.TransactionTypeChooser
import com.emikhalets.myfinances.presentation.theme.AppTheme
import com.emikhalets.myfinances.utils.PreviewEntities
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.safeToDouble
import com.emikhalets.myfinances.utils.toLabelDate
import java.util.*

@Composable
fun TransactionDialog(
    entity: TransactionEntity?,
    categories: List<Category>,
    onDismiss: () -> Unit,
    onSaveClick: (Transaction) -> Unit,
    onDeleteClick: (Transaction) -> Unit,
    injector: TransactionInjector = hiltViewModel(),
) {
    var type by remember { mutableStateOf(entity?.category?.type ?: TransactionType.Expense) }
    var value by remember { mutableStateOf(entity?.transaction?.value?.toString() ?: "0.0") }
    var note by remember { mutableStateOf(entity?.transaction?.note ?: "") }

    var currentCategories by remember { mutableStateOf(categories.filter { it.type == type }) }
    var category by remember { mutableStateOf(entity?.category ?: currentCategories.first()) }

    var valueError by remember { mutableStateOf(false) }

    LaunchedEffect(type) {
        currentCategories = categories.filter { it.type == type }
        category = if (entity?.category?.type == type) {
            entity.category
        } else {
            currentCategories.first()
        }
    }

    AppBaseDialog(
        label = stringResource(R.string.title_category_screen),
        onDismiss = { onDismiss() }
    ) {
        DialogLayout(
            entity = entity,
            categories = currentCategories,
            type = type,
            category = category,
            value = value,
            note = note,
            valueError = valueError,
            onTypeChange = {
                type = it
                category = currentCategories.first()
            },
            onCategoryChange = { category = it },
            onValueChange = {
                Log.d("TAG", "TransactionDialog value change: $it")
                valueError = false
                value = it
            },
            onNoteChange = { note = it },
            onSaveClick = {
                val valueSum = value.safeToDouble { valueError = true }
                valueSum?.let {
                    val savingEntity = entity.copyTransactionOrNew(
                        categoryId = category.id,
                        walletId = injector.prefs.currentWalletId,
                        value = valueSum,
                        type = type,
                        note = note
                    )
                    onSaveClick(savingEntity)
                    onDismiss()
                }
            },
            onDeleteClick = {
                entity?.transaction?.let(onDeleteClick)
                onDismiss()
            }
        )
    }
}

@Composable
private fun DialogLayout(
    entity: TransactionEntity?,
    categories: List<Category>,
    type: TransactionType,
    category: Category,
    value: String,
    note: String,
    valueError: Boolean,
    onTypeChange: (TransactionType) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onValueChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Log.d("TAG", "TransactionDialog value: $value")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        DateText(entity?.transaction?.timestamp)
        Spacer(Modifier.height(16.dp))

        TransactionTypeChooser(type, onTypeChange)
        Spacer(Modifier.height(16.dp))

        CategoriesDropMenu(category, categories, onCategoryChange)
        Spacer(Modifier.height(16.dp))

        AppTextField(
            value = value,
            onValueChange = onValueChange,
            label = stringResource(R.string.label_money_value),
            error = if (valueError) stringResource(R.string.error_invalid_value) else null,
            keyboardType = KeyboardType.Companion.Decimal,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        AppTextField(
            value = note,
            onValueChange = onNoteChange,
            label = stringResource(R.string.label_note),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))

        ControlButtons(entity != null, onSaveClick, onDeleteClick)
    }
}

@Composable
private fun DateText(timestamp: Long?) {
    val date = (timestamp ?: Date().time).toLabelDate()
    AppText(
        text = stringResource(R.string.date_header, date),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
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
    AppTheme {
        AppBaseDialog(
            label = "Preview label",
            onDismiss = {}
        ) {
            DialogLayout(
                entity = PreviewEntities.getTransactionScreenEntity(),
                categories = emptyList(),
                type = TransactionType.Expense,
                category = Category("Default", TransactionType.Expense),
                value = "120.03",
                note = "Some text comment",
                valueError = true,
                onTypeChange = {},
                onCategoryChange = {},
                onValueChange = {},
                onNoteChange = {},
                onSaveClick = {},
                onDeleteClick = {}
            )
        }
    }
}