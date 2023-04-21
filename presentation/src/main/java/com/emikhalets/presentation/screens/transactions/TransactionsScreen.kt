package com.emikhalets.presentation.screens.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.core.formatAmount
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.utils.DayHeaderDateEntity
import com.emikhalets.domain.entity.utils.TransactionsListEntity
import com.emikhalets.presentation.R
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionsScreen(
    type: TransactionType?,
    onTransactionClick: (id: Long, type: TransactionType) -> Unit,
    onBackClick: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getTransactions()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenContent(
            incomeValue = 0.0,
            expensesValue = 0.0,
            totalValue = 0.0,
            transactionsList = emptyList(),
            onTransactionClick = {
//                    id, type -> onTransactionClick(id, type)
            },
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
    incomeValue: Double,
    expensesValue: Double,
    totalValue: Double,
    transactionsList: List<TransactionsListEntity>,
    onTransactionClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SummaryInfoBox(
            incomeValue = incomeValue,
            expensesValue = expensesValue,
            totalValue = totalValue
        )
        TransactionsList(
            list = transactionsList,
            onClick = onTransactionClick
        )
    }
}

@Composable
private fun SummaryInfoBox(
    incomeValue: Double,
    expensesValue: Double,
    totalValue: Double,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            SummaryInfoItemBox(
                title = stringResource(id = R.string.transactions_income),
                value = incomeValue
            )
            SummaryInfoItemBox(
                title = stringResource(id = R.string.transactions_expenses),
                value = expensesValue
            )
            SummaryInfoItemBox(
                title = stringResource(id = R.string.transactions_total),
                value = totalValue
            )
        }
        Divider()
    }
}

@Composable
private fun RowScope.SummaryInfoItemBox(
    title: String,
    value: Double,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
        )
        Text(
            text = value.formatAmount(),
            fontSize = 18.sp,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsList(
    list: List<TransactionsListEntity>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        list.forEach { entity ->
            stickyHeader {
                TransactionsDayHeaderBox(
                    timestamp = entity.timestamp,
                    incomeSum = entity.incomeSum,
                    expensesSum = entity.expensesSum,
                    onClick = {}
                )
            }
            items(entity.transactions) { transactionEntity ->
                TransactionItemBox(
                    entity = transactionEntity,
                    onClick = onClick
                )
                Divider(color = Color.Gray)
            }
        }
    }
}

@Composable
private fun TransactionsDayHeaderBox(
    timestamp: Long,
    incomeSum: Double,
    expensesSum: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp)
        ) {
            DayHeaderDateBox(
                timestamp = timestamp,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = incomeSum.formatAmount(),
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = expensesSum.formatAmount(),
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }
        Divider()
    }
}

@Composable
private fun DayHeaderDateBox(timestamp: Long, modifier: Modifier = Modifier) {
    val (day, dayName, date) = remember { timestamp.splitTransactionsDayHeaderDate() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = day.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Box(
            modifier = Modifier
                .padding(start = 2.dp, end = 6.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Text(
                text = dayName,
                fontSize = 14.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(6.dp, 2.dp)
            )
        }
        Text(
            text = date,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 2.dp)
        )
    }
}

@Composable
private fun TransactionItemBox(
    entity: ComplexTransactionEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Text(
                text = entity.category.name
            )
            Text(
                text = entity.wallet.name
            )
            Text(
                text = entity.transaction.value.formatAmount()
            )
        }
        Divider(color = Color.Gray)
    }
}

private fun Long.splitTransactionsDayHeaderDate(): DayHeaderDateEntity {
    val date = Date(this)
    val day = SimpleDateFormat("d", Locale.getDefault()).format(date)
    val dayName = SimpleDateFormat("EEE", Locale.getDefault()).format(date)
    val dateFormatted = SimpleDateFormat("dd.yyyy", Locale.getDefault()).format(date)
    // TODO: dangerous number formatting
    return DayHeaderDateEntity(
        day = day.toInt(),
        dayName = dayName,
        date = dateFormatted
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            incomeValue = 123.12,
            expensesValue = 123.12,
            totalValue = 123.12,
            transactionsList = listOf(
                TransactionsListEntity(
                    timestamp = Calendar.getInstance().timeInMillis,
                    incomeSum = 123.12,
                    expensesSum = 123.12,
                    transactions = listOf(
                        ComplexTransactionEntity(
                            transaction = TransactionEntity(0,
                                0,
                                0,
                                0,
                                123.12,
                                TransactionType.Expense,
                                "123123"),
                            category = CategoryEntity(0, "Category name", TransactionType.Expense),
                            wallet = WalletEntity(0, "Wallet name", 0, 123.12),
                            currency = CurrencyEntity(0, "asd", "a")
                        ),
                        ComplexTransactionEntity(
                            transaction = TransactionEntity(0,
                                0,
                                0,
                                0,
                                123.12,
                                TransactionType.Expense,
                                "123123"),
                            category = CategoryEntity(0, "Category name", TransactionType.Expense),
                            wallet = WalletEntity(0, "Wallet name", 0, 123.12),
                            currency = CurrencyEntity(0, "asd", "a")
                        ),
                    )
                ),
                TransactionsListEntity(
                    timestamp = Calendar.getInstance().timeInMillis,
                    incomeSum = 123.12,
                    expensesSum = 123.12,
                    transactions = listOf(
                        ComplexTransactionEntity(
                            transaction = TransactionEntity(0,
                                0,
                                0,
                                0,
                                123.12,
                                TransactionType.Expense,
                                "123123"),
                            category = CategoryEntity(0, "Category name", TransactionType.Expense),
                            wallet = WalletEntity(0, "Wallet name", 0, 123.12),
                            currency = CurrencyEntity(0, "asd", "a")
                        ),
                        ComplexTransactionEntity(
                            transaction = TransactionEntity(0,
                                0,
                                0,
                                0,
                                123.12,
                                TransactionType.Expense,
                                "123123"),
                            category = CategoryEntity(0, "Category name", TransactionType.Expense),
                            wallet = WalletEntity(0, "Wallet name", 0, 123.12),
                            currency = CurrencyEntity(0, "asd", "a")
                        ),
                    )
                ),
            ),
            onTransactionClick = {}
        )
    }
}