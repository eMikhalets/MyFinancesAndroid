package com.emikhalets.myfinances.presentation.screens.main

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.copyTransactionOrNew
import com.emikhalets.myfinances.data.entity.withDefault
import com.emikhalets.myfinances.presentation.core.AppBaseDialog
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
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
    val context = LocalContext.current

    val categoriesExpense = remember { mutableListOf<Category>() }
    val categoriesIncome = remember { mutableListOf<Category>() }

    var type by remember {
        mutableStateOf(entity?.category?.type ?: TransactionType.Expense)
    }
    var category by remember {
        mutableStateOf(entity?.category ?: injector.getDefCategory(type))
    }
    var value by remember {
        mutableStateOf(entity?.transaction?.value ?: 0.0)
    }
    var note by remember {
        mutableStateOf(entity?.transaction?.note ?: "")
    }

    val currentCategories = when (type) {
        TransactionType.Expense -> categoriesExpense
        TransactionType.Income -> categoriesIncome
    }

    LaunchedEffect(categories) {
        categoriesExpense.clear()
        categoriesIncome.clear()
        categoriesExpense.addAll(
            categories
                .filter { it.type == TransactionType.Expense }
                .withDefault(context, TransactionType.Expense)
        )
        categoriesIncome.addAll(
            categories
                .filter { it.type == TransactionType.Income }
                .withDefault(context, TransactionType.Income)
        )
    }

    AppBaseDialog(
        label = stringResource(R.string.title_category_screen),
        onDismiss = { onDismiss() },
        padding = 8.dp
    ) {
        DialogLayout(
            entity = entity,
            categories = currentCategories,
            type = type,
            category = category,
            value = value,
            note = note,
            onTypeChange = {
                type = it
                category = Category.getDefaultOld(context, type)
            },
            onCategoryChange = { category = it },
            onValueChange = { value = it.safeToDouble() },
            onNoteChange = { note = it },
            onSaveClick = {
                val savingEntity = entity.copyTransactionOrNew(
                    categoryId = category.id,
                    walletId = injector.prefs.currentWalletId,
                    value = value,
                    type = type,
                    note = note
                )
                onSaveClick(savingEntity)
            },
            onDeleteClick = { entity?.transaction?.let(onDeleteClick) }
        )
    }
}

@Composable
private fun DialogLayout(
    entity: TransactionEntity?,
    categories: List<Category>,
    type: TransactionType,
    category: Category,
    value: Double,
    note: String,
    onTypeChange: (TransactionType) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onValueChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
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

        AppTextField(value.toString(), onValueChange, labelRes = R.string.label_money_value)
        Spacer(Modifier.height(16.dp))

        AppTextField(note, onNoteChange, labelRes = R.string.label_note)
        Spacer(Modifier.height(16.dp))

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
private fun TransactionTypeChooser(
    type: TransactionType,
    onSelectType: (TransactionType) -> Unit,
) {
    Row(Modifier.fillMaxWidth()) {
        AppText(
            text = stringResource(R.string.expenses),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectType(TransactionType.Expense) }
                .borderTypeExpense(type)
                .padding(8.dp)
        )
        AppText(
            text = stringResource(R.string.incomes),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectType(TransactionType.Income) }
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
private fun CategoriesDropMenu(item: Category, list: List<Category>, onSelect: (Category) -> Unit) {
    var selected by remember { mutableStateOf(item) }
    var expanded by remember { mutableStateOf(false) }

    Box {
        Column {
            OutlinedTextField(
                value = (selected.name),
                onValueChange = { onSelect(selected) },
                label = {
                    Text(text = stringResource(R.string.label_category),
                        color = MaterialTheme.colors.onPrimary)
                },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                list.forEach { entry ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            expanded = false
                            onSelect(selected)
                        }
                    ) {
                        Text(
                            text = (entry.name),
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
            }
        }
    }
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
            onDismiss = {},
            padding = 8.dp
        ) {
            DialogLayout(
                entity = PreviewEntities.getTransactionScreenEntity(),
                categories = emptyList(),
                type = TransactionType.Expense,
                category = Category.getDefaultOld(),
                value = 120.03,
                note = "Some text comment",
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