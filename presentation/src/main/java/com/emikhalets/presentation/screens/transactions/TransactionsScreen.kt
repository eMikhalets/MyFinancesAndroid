package com.emikhalets.presentation.screens.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
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
import com.emikhalets.core.formatDate
import com.emikhalets.core.getStartOfNextMonth
import com.emikhalets.core.getStartOfPrevMonth
import com.emikhalets.domain.PreviewEntity
import com.emikhalets.domain.entity.TransactionType
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
    onTransactionClick: (id: Long, type: TransactionType) -> Unit,
    onAddTransactionClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFiltersClick: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var date by remember { mutableStateOf(Date().time) }
    var error by remember { mutableStateOf<UiString?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getTransactions(date)
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            error = uiState.error
            viewModel.dropError()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenContent(
            incomeValue = uiState.incomeSum,
            expensesValue = uiState.expensesSum,
            totalValue = uiState.totalSum,
            transactionsList = uiState.transactionsList,
            date = date.formatDate("MMM yyyy") ?: "-",
            onTransactionClick = onTransactionClick,
            onAddTransactionClick = onAddTransactionClick,
            onDateChanged = { isNext ->
                date = if (isNext) {
                    date.getStartOfNextMonth()
                } else {
                    date.getStartOfPrevMonth()
                }
            },
            onSearchClick = {},
            onFiltersClick = {}
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
    date: String,
    onTransactionClick: (Long, TransactionType) -> Unit,
    onAddTransactionClick: () -> Unit,
    onDateChanged: (Boolean) -> Unit,
    onSearchClick: () -> Unit,
    onFiltersClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            onClick = onAddTransactionClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(
                date = date,
                onDateChanged = onDateChanged,
                onSearchClick = onSearchClick,
                onFiltersClick = onFiltersClick
            )
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
}

@Composable
private fun ScreenHeader(
    date: String,
    onDateChanged: (Boolean) -> Unit,
    onSearchClick: () -> Unit,
    onFiltersClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                contentDescription = null,
                modifier = Modifier.clickable { onDateChanged(false) }
            )
            Text(
                text = date,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.clickable { onDateChanged(true) }
            )
        }
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = null,
            modifier = Modifier.clickable { onSearchClick() }
        )
        Icon(
            imageVector = Icons.Rounded.FilterAlt,
            contentDescription = null,
            modifier = Modifier
                .clickable { onFiltersClick() }
                .padding(start = 8.dp)
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
                value = incomeValue,
                color = MaterialTheme.colors.primary
            )
            SummaryInfoItemBox(
                title = stringResource(id = R.string.transactions_expenses),
                value = expensesValue,
                color = MaterialTheme.colors.error
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
    color: Color = MaterialTheme.colors.onSurface,
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
            color = color
        )
        Text(
            text = value.formatAmount(),
            fontSize = 18.sp,
            color = color
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsList(
    list: List<TransactionsListEntity>,
    onClick: (Long, TransactionType) -> Unit,
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
                fontSize = 16.sp,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = expensesSum.formatAmount(),
                fontSize = 16.sp,
                color = MaterialTheme.colors.error,
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
    onClick: (Long, TransactionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick(entity.transaction.id, entity.transaction.type) }
        ) {
            Text(
                text = entity.category.name,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )
            Text(
                text = entity.wallet.name,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
            Text(
                text = entity.transaction.value.formatAmount(),
                fontSize = 16.sp,
                color = if (entity.transaction.type == TransactionType.Expense) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.primary
                },
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
        }
        Divider()
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
                    transactions = PreviewEntity.getComplexTransactionsList(10)
                ),
                TransactionsListEntity(
                    timestamp = Calendar.getInstance().timeInMillis,
                    incomeSum = 123.12,
                    expensesSum = 123.12,
                    transactions = PreviewEntity.getComplexTransactionsList(5)
                ),
            ),
            date = "Apr 2023",
            onTransactionClick = { _, _ -> },
            onAddTransactionClick = {},
            onDateChanged = {},
            onSearchClick = {},
            onFiltersClick = {}
        )
    }
}