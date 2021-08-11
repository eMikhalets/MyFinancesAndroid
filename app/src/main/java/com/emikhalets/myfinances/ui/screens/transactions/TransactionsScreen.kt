package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.ui.base.LoaderScreen
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.navigateToNewTransaction

@Composable
fun TransactionsScreen(
    navController: NavHostController,
    viewModel: TransactionsVM = hiltViewModel()
) {
    Column(Modifier.fillMaxSize()) {
        when (val state = viewModel.state) {
            is TransactionsState.Transactions -> {
                TransactionsList(
                    navController = navController,
                    list = state.list,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
            is TransactionsState.Error -> {
            }
            TransactionsState.EmptyTransactions -> {
                TransactionsEmpty(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
            TransactionsState.Loading -> {
                LoaderScreen()
            }
            TransactionsState.Idle -> {
                viewModel.getTransactions()
                TransactionsIdle()
            }
        }
        AddTransaction(
            navController = navController,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TransactionsIdle() {
    Column(Modifier.fillMaxSize()) {
    }
}

@Composable
fun TransactionsEmpty(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.empty_transactions))
    }
}

@Composable
fun TransactionsList(
    navController: NavHostController,
    list: List<Transaction>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(list.size) {
        }
    }
}

@Composable
fun AddTransaction(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Button(
            onClick = { navController.navigateToNewTransaction(TransactionType.EXPENSE) }
        ) {
            Text(text = "Expense")
        }
        Spacer(modifier = Modifier.width(80.dp))
        Button(
            onClick = { navController.navigateToNewTransaction(TransactionType.INCOME) }
        ) {
            Text(text = "Income")
        }
    }
}