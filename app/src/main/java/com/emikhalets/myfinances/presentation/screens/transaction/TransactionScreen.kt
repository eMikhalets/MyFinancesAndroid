package com.emikhalets.myfinances.presentation.screens.transaction

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.withDefault
import com.emikhalets.myfinances.presentation.core.AppText
import com.emikhalets.myfinances.presentation.core.AppTextButton
import com.emikhalets.myfinances.presentation.core.AppTextField
import com.emikhalets.myfinances.presentation.core.AppToolbar
import com.emikhalets.myfinances.presentation.core.ScreenScaffold
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.PreviewEntities
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.safeToDouble
import com.emikhalets.myfinances.utils.toDate
import com.emikhalets.myfinances.utils.toast

@Composable
fun TransactionScreen(
    navController: NavHostController,
    transactionId: Long,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current
    val defCategory = Category.getDefault()

    val categories = remember { mutableListOf<Category>() }

    var note by remember { mutableStateOf("") }
    var value by remember { mutableStateOf(0.0) }
    var type by remember { mutableStateOf(TransactionType.Expense) }
    var category by remember { mutableStateOf(defCategory) }

    LaunchedEffect(Unit) { viewModel.getTransaction(transactionId) }

    LaunchedEffect(state.entity) {
        if (state.entity != null) {
            note = state.entity.transaction.note
            value = state.entity.transaction.value
            type = state.entity.transaction.type
            category = state.entity.category
            viewModel.getCategories(state.entity.transaction.type)
        }
    }

    LaunchedEffect(state.categories) {
        categories.clear()
        categories.addAll(state.categories.withDefault(context, type))
    }

    LaunchedEffect(state.transactionSaved) {
        if (state.transactionSaved) navController.popBackStack()
    }

    LaunchedEffect(state.transactionDeleted) {
        if (state.transactionDeleted) navController.popBackStack()
    }

    LaunchedEffect(state.error) { toast(context, state.error) }

    TransactionScreen(
        navController = navController,
        entity = state.entity,
        categories = categories,
        category = category,
        note = note,
        value = value,
        type = type,
        onTypeChange = {
            type = it
            category = Category.getDefault(context, type)
            viewModel.getCategories(type)
        },
        onValueChange = { value = it.safeToDouble() },
        onNoteChange = { note = it },
        onCategoryChange = { category = it },
        onSaveClick = { viewModel.saveTransaction(category, value, type, note) },
        onDeleteClick = viewModel::deleteTransaction
    )
}

@Composable
private fun TransactionScreen(
    navController: NavHostController,
    entity: TransactionEntity?,
    categories: List<Category>,
    category: Category,
    note: String,
    value: Double,
    type: TransactionType,
    onTypeChange: (TransactionType) -> Unit,
    onValueChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    entity ?: return
    ScreenScaffold({
        AppToolbar(navController, stringResource(R.string.title_transaction_screen))
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DateText(entity.transaction.timestamp)
            Spacer(Modifier.height(16.dp))

            TransactionTypeChooser(type, onTypeChange)
            Spacer(Modifier.height(16.dp))

            CategoriesDropMenu(category, categories, onCategoryChange)
            Spacer(Modifier.height(16.dp))

            AppTextField(value.toString(), onValueChange, labelRes = R.string.label_money_value)
            Spacer(Modifier.height(16.dp))

            AppTextField(note, onNoteChange, labelRes = R.string.label_note)
            Spacer(Modifier.height(16.dp))

            ControlButtons(onSaveClick, onDeleteClick)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DateText(timestamp: Long) {
    AppText(
        text = stringResource(R.string.date_header, timestamp.toDate()),
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
fun CategoriesDropMenu(item: Category, list: List<Category>, onSelect: (Category) -> Unit) {
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
fun ControlButtons(onSaveClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.delete),
            onClick = onDeleteClick,
            modifier = Modifier.weight(1f)
        )
        AppTextButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailsPreview() {
    MyFinancesTheme {
        TransactionScreen(
            navController = rememberNavController(),
            entity = PreviewEntities.getTransactionScreenEntity(),
            categories = emptyList(),
            category = Category.getDefault(),
            note = "Some text comment",
            value = 120.03,
            type = TransactionType.Expense,
            onTypeChange = {},
            onValueChange = {},
            onNoteChange = {},
            onCategoryChange = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}