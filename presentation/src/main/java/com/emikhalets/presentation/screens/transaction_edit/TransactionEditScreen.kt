package com.emikhalets.presentation.screens.transaction_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.domain.entity.copyOrNew
import com.emikhalets.myfinances.presentation.core.AppCategorySpinner
import com.emikhalets.myfinances.presentation.core.TextPrimary
import com.emikhalets.myfinances.presentation.core.TransactionKeyboard
import com.emikhalets.presentation.core.AppTextButton
import com.emikhalets.presentation.core.AppTextField
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.core.TransactionTypeChooser
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.boxBackground

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
        AppTopAppBar(title = stringResource(Screen.TransactionEdit.title),
            onBackClick = onBackClick)
        ScreenContent(
            id = id,
            value = value,
            transactionType = transactionType,
            note = note,
            valueError = valueError,
            onValueChange = {
                valueError = ""
                value = it
            },
            onTypeChange = { transactionType = it },
            onDeleteClick = { viewModel.deleteTransaction() },
            onSaveClick = {
                val entity = TransactionEntity(
                    id = id,
                    categoryId = categoryId,
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

@Composable
private fun ScreenContent(
    id: Long,
    value: Double,
    transactionType: TransactionType,
    note: String,
    valueError: String,
    onValueChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        TransactionTypeChooser(type = type, onTypeSelect = {
            type = it
            onTypeChange(it)
        })
        Spacer(modifier = Modifier.height(16.dp))
        AppCategorySpinner(categories = categories,
            initItem = category,
            onSelect = { category = it })
        AppTextField(value = note, onValueChange = { note = it })
        TransactionKeyboard(value = value, onValueChange = { value = it })
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.boxBackground)
                .clickable {
                    val saveTransaction =
                        entity?.transaction.copyOrNew(category.id, value, type, note)
                    onSaveClick(saveTransaction)
                }
                .padding(16.dp)) {
            TextPrimary(text = stringResource(R.string.app_save), fontSize = 20.sp)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TransactionTypeChooser(
            selectedType = transactionType,
            onTypeSelect = onTypeChange
        )
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            label = stringResource(R.string.label_name),
            error = nameError.ifEmpty { null },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            if (id > 0) {
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
private fun Preview() {
    AppTheme {
        ScreenContent(
            id = 0,
            name = "Test name",
            transactionType = TransactionType.Expense,
            nameError = "",
            onNameChange = {},
            onTypeChange = {},
            onDeleteClick = {},
            onSaveClick = {},
        )
    }
}