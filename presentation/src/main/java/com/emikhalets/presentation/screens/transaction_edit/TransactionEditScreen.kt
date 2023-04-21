package com.emikhalets.presentation.screens.transaction_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.DEFAULT_CATEGORY_EXPENSE_ID
import com.emikhalets.core.DEFAULT_CATEGORY_INCOME_ID
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.presentation.core.AppCategoriesSpinner
import com.emikhalets.presentation.core.AppTextButton
import com.emikhalets.presentation.core.AppTextField
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.core.TransactionTypeChooser
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun TransactionEditScreen(
    transactionId: Long?,
    type: TransactionType?,
    onBackClick: () -> Unit,
    viewModel: TransactionEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var id by remember { mutableStateOf(transactionId ?: 0L) }
    var categoryId by remember { mutableStateOf(0L) }
    var walletId by remember { mutableStateOf(0L) }
    var currencyId by remember { mutableStateOf(0L) }
    var value by remember { mutableStateOf(0.0) }
    var transactionType by remember { mutableStateOf(type ?: TransactionType.Expense) }
    var note by remember { mutableStateOf("") }
    var valueError by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<CategoryEntity?>(null) }
    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        if (transactionId != null) {
            viewModel.getTransaction(transactionId)
        }
    }

    LaunchedEffect(transactionType) {
        viewModel.getCategories(transactionType)
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    LaunchedEffect(uiState.categories) {
        if (uiState.categories.isNotEmpty()) {
            category = uiState.categories.first()
        }
    }

    LaunchedEffect(uiState.transaction) {
        val entity = uiState.transaction
        if (entity != null) {
            id = entity.id
            categoryId = entity.categoryId
            walletId = entity.walletId
            currencyId = entity.currencyId
            value = entity.value
            transactionType = entity.type
            note = entity.note
        }
    }

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onBackClick()
        }
    }

    LaunchedEffect(uiState.deleted) {
        if (uiState.deleted) {
            onBackClick()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        AppTopAppBar(title = stringResource(Screen.TransactionEdit.title),
//            onBackClick = onBackClick)
        ScreenContent(
            id = id,
            value = value,
            transactionType = transactionType,
            note = note,
            valueError = valueError,
            category = category,
            categories = uiState.categories,
            onValueChange = {
                valueError = ""
                value = it.toDouble()
            },
            onTypeChange = { transactionType = it },
            onNoteChange = { note = it },
            onCategoryChange = { category = it },
            onDeleteClick = { viewModel.deleteTransaction() },
            onSaveClick = {
                val entity = TransactionEntity(
                    id = id,
                    categoryId = category?.id ?: getCategoryId(transactionType),
                    walletId = walletId,
                    currencyId = currencyId,
                    value = value,
                    type = transactionType,
                    note = note
                )
                viewModel.saveTransaction(entity)
            }
        )
    }

    val errorMessage = error
    if (errorMessage != null) {
        MessageDialog(
            message = errorMessage.asString(),
            onDismiss = { error = null }
        )
    }
}

private fun getCategoryId(type: TransactionType): Long {
    return when (type) {
        TransactionType.Expense -> DEFAULT_CATEGORY_EXPENSE_ID
        TransactionType.Income -> DEFAULT_CATEGORY_INCOME_ID
    }
}

@Composable
private fun ScreenContent(
    id: Long,
    value: Double,
    transactionType: TransactionType,
    note: String,
    valueError: String,
    category: CategoryEntity?,
    categories: List<CategoryEntity>,
    onValueChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onCategoryChange: (CategoryEntity) -> Unit,
    onNoteChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        TransactionTypeChooser(transactionType, onTypeChange)
        category?.let {
            AppCategoriesSpinner(
                categoriesList = categories,
                selectedCategory = category,
                onSelectCategory = onCategoryChange,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        AppTextField(
            value = value.toString(),
            onValueChange = onValueChange,
            label = "stringResource(R.string.label_name)",
            error = valueError.ifEmpty { null },
            modifier = Modifier.fillMaxWidth()
        )
        AppTextField(
            value = note,
            onValueChange = onNoteChange,
            label = "stringResource(R.string.label_name)",
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            if (id > 0) {
                AppTextButton(
                    text = "stringResource(R.string.app_delete)",
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f)
                )
            }
            AppTextButton(
                text = "stringResource(R.string.app_save)",
                onClick = onSaveClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            id = 0,
            value = 123.42,
            transactionType = TransactionType.Income,
            note = "note",
            valueError = "valueError",
            category = CategoryEntity(1, "Category 2", TransactionType.Income),
            categories = listOf(
                CategoryEntity(0, "Category 1", TransactionType.Income),
                CategoryEntity(1, "Category 2", TransactionType.Income),
                CategoryEntity(2, "Category 3", TransactionType.Income),
            ),
            onValueChange = {},
            onTypeChange = {},
            onNoteChange = {},
            onCategoryChange = {},
            onDeleteClick = {},
            onSaveClick = {}
        )
    }
}