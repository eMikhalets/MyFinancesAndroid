package com.emikhalets.presentation.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.core.UiString
import com.emikhalets.core.toDate
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.presentation.core.AppTopAppBar
import com.emikhalets.presentation.dialog.MessageDialog
import com.emikhalets.presentation.navigation.Screen
import com.emikhalets.presentation.theme.AppTheme

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
        AppTopAppBar(title = stringResource(Screen.Transactions.title), onBackClick = onBackClick)
        ScreenContent(
            expenses = uiState.expenseList,
            incomes = uiState.incomeList,
            type = type ?: TransactionType.Expense,
            onTransactionClick = { id, type -> onTransactionClick(id, type) },
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
    expenses: List<TransactionEntity>,
    incomes: List<TransactionEntity>,
    type: TransactionType,
    onTransactionClick: (Long, TransactionType) -> Unit,
) {
    val list = when (type) {
        TransactionType.Expense -> expenses
        TransactionType.Income -> incomes
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(list) { entity ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { onTransactionClick(entity.id, entity.type) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "entity.name",
                                fontSize = 18.sp
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(
                                text = entity.timestamp.toDate(),
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            text = entity.note,
                            fontSize = 14.sp)
                    }
                    Text(
                        text = entity.value.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Divider(color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            expenses = listOf(
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Expense, "123123"),
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Expense, "123123"),
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Expense, "123123"),
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Expense, "123123"),
            ),
            incomes = listOf(
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Income, "123123"),
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Income, "123123"),
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Income, "123123"),
                TransactionEntity(0, 0, 0, 0, 123.12, TransactionType.Income, "123123"),
            ),
            type = TransactionType.Expense,
            onTransactionClick = { _, _ -> },
        )
    }
}