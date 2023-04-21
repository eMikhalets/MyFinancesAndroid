package com.emikhalets.presentation.screens.currencies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.presentation.core.AppButton
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.textSecondary

@Composable
fun CurrenciesScreen(
    onCurrencyClick: (id: Long) -> Unit,
    onAddCurrencyClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CurrenciesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getCurrencies()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        AppTopAppBar(title = stringResource(Screen.Currencies.title), onBackClick = onBackClick)
        ScreenContent(
            currencies = uiState.currencies,
            onCurrencyClick = { id -> onCurrencyClick(id) },
            onAddCurrencyClick = { onAddCurrencyClick() }
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
    currencies: List<CurrencyEntity>,
    onCurrencyClick: (Long) -> Unit,
    onAddCurrencyClick: () -> Unit,
) {
    CurrenciesList(
        currencies = currencies,
        onCurrencyClick = { id -> onCurrencyClick(id) },
        onAddCurrencyClick = onAddCurrencyClick,
    )
}

@Composable
private fun CurrenciesList(
    currencies: List<CurrencyEntity>,
    onCurrencyClick: (Long) -> Unit,
    onAddCurrencyClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(currencies) { currency ->
                CurrencyItem(
                    currency = currency,
                    onCurrencyClick = onCurrencyClick
                )
            }
        }
        Divider()
        AppButton(
            text = "stringResource(R.string.add)",
            onClick = onAddCurrencyClick
        )
    }
}

@Composable
private fun CurrencyItem(currency: CurrencyEntity, onCurrencyClick: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCurrencyClick(currency.id) }
    ) {
        Text(
            text = "${currency.symbol}\t${currency.name}",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Divider(color = MaterialTheme.colors.textSecondary)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            currencies = listOf(
                CurrencyEntity(0, "Name 1", "P"),
                CurrencyEntity(0, "Name 1", "P"),
                CurrencyEntity(0, "Name 1", "P"),
                CurrencyEntity(0, "Name 1", "P"),
                CurrencyEntity(0, "Name 1", "P"),
                CurrencyEntity(0, "Name 1", "P"),
            ),
            onCurrencyClick = {},
            onAddCurrencyClick = {}
        )
    }
}