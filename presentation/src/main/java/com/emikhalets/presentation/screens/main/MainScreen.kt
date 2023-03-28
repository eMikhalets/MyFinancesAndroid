package com.emikhalets.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.HorizontalRule
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme
import com.emikhalets.presentation.theme.boxBackground

@Composable
fun MainScreen(
    onTransactionsClick: (type: TransactionType) -> Unit,
    onTransactionEditClick: (type: TransactionType) -> Unit,
    onCategoriesClick: () -> Unit,
    onWalletsClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getCurrentWalletInfo()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(title = stringResource(Screen.Main.title))
        ScreenContent(
            walletName = uiState.walletName,
            incomeSum = uiState.incomeSum,
            expenseSum = uiState.expenseSum,
            onExpensesClick = { onTransactionsClick(TransactionType.Expense) },
            onIncomesClick = { onTransactionsClick(TransactionType.Income) },
            onAddExpenseClick = { onTransactionEditClick(TransactionType.Expense) },
            onAddIncomeClick = { onTransactionEditClick(TransactionType.Income) },
            onCategoriesClick = { onCategoriesClick() },
            onWalletsClick = { onWalletsClick() },
            onCurrenciesClick = { onCurrenciesClick() },
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
    walletName: String?,
    incomeSum: Double?,
    expenseSum: Double?,
    onExpensesClick: () -> Unit,
    onIncomesClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onWalletsClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (!walletName.isNullOrEmpty()) {
            Text(text = walletName)
        }
        if (incomeSum != null && expenseSum != null) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                ValueBox(incomeSum, TransactionType.Income, onIncomesClick)
                ValueBox(expenseSum, TransactionType.Expense, onExpensesClick)
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        ) {
            MainButton(
                text = "stringResource(R.string.main_)",
                icon = Icons.Rounded.Bookmark,
                onClick = onCategoriesClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = "stringResource(R.string.app_budget)",
                icon = Icons.Rounded.Wallet,
                onClick = onWalletsClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = "stringResource(R.string.app_charts)",
                icon = Icons.Rounded.Money,
                onClick = onCurrenciesClick
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            MainButton(
                text = "stringResource(R.string.app_add_expense)",
                icon = Icons.Rounded.HorizontalRule,
                onClick = onAddExpenseClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = "stringResource(R.string.app_add_income)",
                icon = Icons.Rounded.Add,
                onClick = onAddIncomeClick
            )
        }
    }
}

@Composable
private fun ValueBox(value: Double, type: TransactionType, onClick: () -> Unit) {
    val title by remember { mutableStateOf(getValueBoxTitle(type)) }
    val valuePrefix by remember { mutableStateOf(getValueBoxPrefix(type)) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.boxBackground, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(150.dp)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${stringResource(valuePrefix)} $value",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun RowScope.MainButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.boxBackground, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            incomeSum = 7057.64,
            expenseSum = 7057.64,
            walletName = "Test wallet name",
            onExpensesClick = {},
            onIncomesClick = {},
            onAddExpenseClick = {},
            onAddIncomeClick = {},
            onCategoriesClick = {},
            onWalletsClick = {},
            onCurrenciesClick = {},
        )
    }
}

private fun getValueBoxTitle(type: TransactionType): Int {
    return when (type) {
//        TransactionType.Expense -> R.string.app_expenses
//        TransactionType.Income -> R.string.app_incomes
        TransactionType.Expense -> 0
        TransactionType.Income -> 0
    }
}

private fun getValueBoxPrefix(type: TransactionType): Int {
    return when (type) {
        TransactionType.Expense -> 0
        TransactionType.Income -> 0
//        TransactionType.Expense -> R.string.app_minus
//        TransactionType.Income -> R.string.app_plus
    }
}