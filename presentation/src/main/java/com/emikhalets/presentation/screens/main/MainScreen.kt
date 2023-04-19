package com.emikhalets.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.core.formatValue
import com.emikhalets.domain.PreviewEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import com.emikhalets.presentation.R
import com.emikhalets.presentation.core.AppScaffold
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme

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
        viewModel.getLastTransactions()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    ScreenContent(
        complexWallet = uiState.complexWallet,
        incomeSum = uiState.incomeSum,
        expenseSum = uiState.expenseSum,
        lastTransactions = uiState.lastTransactions,
        onExpensesClick = { onTransactionsClick(TransactionType.Expense) },
        onIncomesClick = { onTransactionsClick(TransactionType.Income) },
        onAddExpenseClick = { onTransactionEditClick(TransactionType.Expense) },
        onAddIncomeClick = { onTransactionEditClick(TransactionType.Income) },
        onCategoriesClick = { onCategoriesClick() },
        onWalletsClick = { onWalletsClick() },
        onCurrenciesClick = { onCurrenciesClick() },
    )

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
    complexWallet: ComplexWalletEntity?,
    incomeSum: Double?,
    expenseSum: Double?,
    lastTransactions: List<ComplexTransactionEntity>,
    onExpensesClick: () -> Unit,
    onIncomesClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onWalletsClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
) {

//    AppScaffold {
//        AppTopAppBar(title = stringResource(Screen.Main.title))
        Column(modifier = Modifier.fillMaxWidth()) {
            WalletInfoBox(
                name = complexWallet?.wallet?.name ?: "",
                currency = complexWallet?.currency?.symbol ?: "",
                expenseSum = expenseSum ?: 0.0,
                incomeSum = incomeSum ?: 0.0,
                onExpensesClick = onExpensesClick,
                onIncomesClick = onIncomesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            LastTransactionsBox(
                list = lastTransactions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .weight(1f)
            )
            ButtonsBox(
                onCategoriesClick = onCategoriesClick,
                onWalletsClick = onWalletsClick,
                onCurrenciesClick = onCurrenciesClick,
                onAddExpenseClick = onAddExpenseClick,
                onAddIncomeClick = onAddIncomeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
//    }
}

@Composable
private fun WalletInfoBox(
    name: String,
    currency: String,
    expenseSum: Double,
    incomeSum: Double,
    onExpensesClick: () -> Unit,
    onIncomesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = currency,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            TransactionsSumBox(
                value = incomeSum,
                type = TransactionType.Income,
                onClick = onIncomesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TransactionsSumBox(
                value = expenseSum,
                type = TransactionType.Expense,
                onClick = onExpensesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
private fun TransactionsSumBox(
    value: Double,
    type: TransactionType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title by remember { mutableStateOf(getTransactionsSumTitle(type)) }
    val color = getTransactionColor(type)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colors.background)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.formatValue(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = color,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

private fun getTransactionsSumTitle(type: TransactionType): Int {
    return when (type) {
        TransactionType.Expense -> R.string.main_expenses
        TransactionType.Income -> R.string.main_incomes
    }
}

@Composable
private fun LastTransactionsBox(
    list: List<ComplexTransactionEntity>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.main_last_transactions),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        )
        LazyColumn(
            modifier = modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.background)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(4.dp)
        ) {
            items(list) { item ->
                LastTransactionsBox(
                    categoryName = item.category.name,
                    note = item.transaction.note,
                    type = item.transaction.type,
                    value = item.transaction.value,
                    currencySymbol = item.currency.symbol,
                    modifier = Modifier,
                )
            }
        }
    }
}

@Composable
private fun LastTransactionsBox(
    categoryName: String,
    note: String,
    type: TransactionType,
    value: Double,
    currencySymbol: String,
    modifier: Modifier = Modifier,
) {
    val valueColor = getTransactionColor(type)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(3f)
            ) {
                Text(
                    text = categoryName,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground
                )
                if (note.isNotEmpty()) {
                    Text(
                        text = note,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                Text(
                    text = "$currencySymbol ${value.formatValue()}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = valueColor
                )
            }
        }
        Divider()
    }
}

@Composable
private fun getTransactionColor(type: TransactionType): Color {
    return when (type) {
        TransactionType.Expense -> MaterialTheme.colors.error
        TransactionType.Income -> MaterialTheme.colors.onBackground
    }
}

@Composable
private fun ButtonsBox(
    onCategoriesClick: () -> Unit,
    onWalletsClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            MainButton(
                text = stringResource(R.string.main_categories),
                icon = Icons.Rounded.Bookmark,
                onClick = onCategoriesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = stringResource(R.string.main_wallets),
                icon = Icons.Rounded.Wallet,
                onClick = onWalletsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            MainButton(
                text = stringResource(R.string.main_currencies),
                icon = Icons.Rounded.Money,
                onClick = onCurrenciesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            NewTransactionButton(
                icon = Icons.Rounded.HorizontalRule,
                onClick = onAddExpenseClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            NewTransactionButton(
                icon = Icons.Rounded.Add,
                onClick = onAddIncomeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun MainButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(8.dp)
            .aspectRatio(1f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun NewTransactionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = icon,
        contentDescription = "new transaction icon",
        tint = MaterialTheme.colors.onPrimary,
        modifier = modifier
            .size(50.dp)
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            incomeSum = 7057.64,
            expenseSum = 7057.64,
            lastTransactions = PreviewEntity.getComplexTransactionsList(20),
            complexWallet = PreviewEntity.getComplexWallet(),
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