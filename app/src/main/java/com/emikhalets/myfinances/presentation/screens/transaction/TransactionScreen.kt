package com.emikhalets.myfinances.presentation.screens.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.data.entity.withDefault
import com.emikhalets.myfinances.presentation.core.*
import com.emikhalets.myfinances.presentation.theme.MyFinancesTheme
import com.emikhalets.myfinances.utils.AnimateExpandCollapse
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
            AppText(
                text = stringResource(R.string.date_header, entity.transaction.timestamp.toDate()),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(Modifier.height(16.dp))

            TransactionTypeChooser(type, onTypeChange)
            Spacer(Modifier.height(16.dp))

            AppTextField(value.toString(), onValueChange)
            Spacer(Modifier.height(16.dp))

            NoteTextField(note = note, onNoteChange = onNoteChange)
            Spacer(Modifier.height(16.dp))

            Keyboard(onClick = onValueChange)
            Spacer(Modifier.height(16.dp))

            CategoriesLayout(
                selected = category,
                categories = categories,
                onCategoryClick = onCategoryClick
            )
            Spacer(Modifier.height(16.dp))

            ControlButtons(onSaveClick = onSaveClick, onDeleteClick = onDeleteClick)
            Spacer(Modifier.height(16.dp))
        }
    }
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
fun CategoriesLayout(
    selected: Category?,
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
    ) {
        AppText(
            text = selected?.name ?: stringResource(R.string.choose_category),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .clip(RoundedCornerShape(4.dp))
                .padding(16.dp)
        )
        AnimateExpandCollapse(visible = expanded, duration = 300) {
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            categories.forEach { category ->
                AppText(
                    text = category.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategoryClick(category) }
                        .padding(16.dp)
                )
            }
            AppText(
                text = stringResource(R.string.new_category),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ControlButtons(onSaveClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        AppTextButton(
            text = stringResource(R.string.cancel),
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
            transactionType = TransactionType.Expense,
            onTypeChange = {},
            value = "120.03",
            onValueChange = {},
            note = "",
            onNoteChange = {},
            category = null,
            categories = emptyList(),
            onCategoryClick = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}