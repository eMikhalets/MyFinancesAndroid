package com.emikhalets.presentation.screens.currency_edit

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
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.myfinances.R
import com.emikhalets.presentation.core.AppTextButton
import com.emikhalets.presentation.core.AppTextField
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun CurrencyEditScreen(
    currencyId: Long?,
    onBackClick: () -> Unit,
    viewModel: CurrencyEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var id by remember { mutableStateOf(0L) }
    var name by remember { mutableStateOf("") }
    var symbol by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        if (currencyId != null) {
            viewModel.getCurrency(currencyId)
        }
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    LaunchedEffect(uiState.currency) {
        val entity = uiState.currency
        if (entity != null) {
            id = entity.id
            name = entity.name
            symbol = entity.symbol
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

    LaunchedEffect(uiState.existed) {
        if (uiState.existed) {
            nameError = getString(R.string.existed)
            viewModel.dropCurrencyExisted()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(title = stringResource(Screen.CurrencyEdit.title), onBackClick = onBackClick)
        ScreenContent(
            id = id,
            name = name,
            symbol = symbol,
            onNameChange = {
                nameError = ""
                name = it
            },
            onDeleteClick = { viewModel.deleteCurrency() },
            onSaveClick = {
                if (name.isEmpty()) {
                    nameError = getString(R.string.empty)
                } else {
                    val entity = CurrencyEntity(id, name, symbol)
                    viewModel.saveCurrency(entity)
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
    symbol: String,
    nameError: String,
    onNameChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
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
            symbol = "P",
            nameError = "",
            onNameChange = {},
            onDeleteClick = {},
            onSaveClick = {},
        )
    }
}