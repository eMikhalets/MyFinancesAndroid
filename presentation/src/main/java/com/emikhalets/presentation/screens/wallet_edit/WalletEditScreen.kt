package com.emikhalets.presentation.screens.wallet_edit

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
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.presentation.core.AppCurrenciesSpinner
import com.emikhalets.presentation.core.AppTextButton
import com.emikhalets.presentation.core.AppTextField
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme

@Composable
fun WalletEditScreen(
    walletId: Long?,
    onBackClick: () -> Unit,
    viewModel: WalletEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var id by remember { mutableStateOf(0L) }
    var name by remember { mutableStateOf("") }
    var currencyId by remember { mutableStateOf(0L) }
    var currency by remember { mutableStateOf<CurrencyEntity?>(null) }
    var initValue by remember { mutableStateOf(0.0) }
    var valueError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getCurrencies()
        if (walletId != null) {
            viewModel.getWallet(walletId)
        }
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    LaunchedEffect(uiState.wallet) {
        val entity = uiState.wallet
        if (entity != null) {
            id = entity.id
            name = entity.name
            currencyId = entity.currencyId
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
            nameError = "getString(R.string.existed)"
            viewModel.dropExisted()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        AppTopAppBar(title = stringResource(Screen.WalletEdit.title), onBackClick = onBackClick)
        ScreenContent(
            id = id,
            name = name,
            initValue = initValue,
            nameError = nameError,
            valueError = valueError,
            currencies = uiState.currencies,
            currency = currency,
            onNameChange = {
                nameError = ""
                name = it
            },
            onValueChange = {
                valueError = ""
                initValue = it
            },
            onCurrencyChange = { viewModel.deleteEntity() },
            onDeleteClick = { viewModel.deleteEntity() },
            onSaveClick = {
                if (name.isEmpty()) {
                    nameError = "getString(R.string.empty)"
                } else {
                    val entity = WalletEntity(id, name, currencyId, initValue)
                    viewModel.saveEntity(entity)
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
    initValue: Double,
    currency: CurrencyEntity?,
    nameError: String,
    valueError: String,
    currencies: List<CurrencyEntity>,
    onNameChange: (String) -> Unit,
    onValueChange: (Double) -> Unit,
    onCurrencyChange: (CurrencyEntity) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppCurrenciesSpinner(
            list = currencies,
            selected = currency ?: CurrencyEntity(0, "", ""),
            onSelect = onCurrencyChange
        )
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            label = "stringResource(R.string.label_name)",
            error = nameError.ifEmpty { null },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        if (id <= 0) {
            AppTextField(
                value = initValue.toString(),
                onValueChange = { onValueChange(it.toDouble()) },
                label = "stringResource(R.string.label_name)",
                error = valueError.ifEmpty { null },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
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
            name = "Test name",
            initValue = 24.85,
            nameError = "",
            valueError = "",
            currencies = emptyList(),
            currency = CurrencyEntity(0, "", ""),
            onNameChange = {},
            onDeleteClick = {},
            onValueChange = { },
            onCurrencyChange = {},
            onSaveClick = {}
        )
    }
}