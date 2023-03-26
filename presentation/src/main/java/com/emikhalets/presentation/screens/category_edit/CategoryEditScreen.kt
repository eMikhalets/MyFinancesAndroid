package com.emikhalets.presentation.screens.category_edit

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
import com.emikhalets.core.UiString
import com.emikhalets.core.getString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.myfinances.R
import com.emikhalets.presentation.core.AppTextButton
import com.emikhalets.presentation.core.AppTextField
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.core.TransactionTypeChooser
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun CategoryEditScreen(
    categoryId: Long?,
    type: TransactionType?,
    onBackClick: () -> Unit,
    viewModel: CategoryEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var id by remember { mutableStateOf(0L) }
    var name by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf(type ?: TransactionType.Expense) }
    var nameError by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        if (categoryId != null) {
            viewModel.getCategory(categoryId)
        }
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    LaunchedEffect(uiState.category) {
        val entity = uiState.category
        if (entity != null) {
            id = entity.id
            name = entity.name
            transactionType = entity.type
        }
    }

    LaunchedEffect(uiState.categorySaved) {
        if (uiState.categorySaved) {
            onBackClick()
        }
    }

    LaunchedEffect(uiState.categoryDeleted) {
        if (uiState.categoryDeleted) {
            onBackClick()
        }
    }

    LaunchedEffect(uiState.categoryExisted) {
        if (uiState.categoryExisted) {
            nameError = getString(R.string.existed)
            viewModel.dropCategoryExisted()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(title = stringResource(Screen.CategoryEdit.title), onBackClick = onBackClick)
        ScreenContent(
            id = id,
            name = name,
            transactionType = transactionType,
            onNameChange = {
                nameError = ""
                name = it
            },
            onTypeChange = { transactionType = it },
            onDeleteClick = { viewModel.deleteCategory() },
            onSaveClick = {
                if (name.isEmpty()) {
                    nameError = getString(R.string.empty)
                } else {
                    val entity = CategoryEntity(id, name, transactionType)
                    viewModel.saveCategory(entity)
                }
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
    name: String,
    transactionType: TransactionType,
    nameError: String,
    onNameChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TransactionTypeChooser(
            selectedType = transactionType,
            onTypeSelect = onTypeChange
        )
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            label = stringResource(R.string.label_name),
            error = if (nameError.isNotEmpty()) nameError else null,
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