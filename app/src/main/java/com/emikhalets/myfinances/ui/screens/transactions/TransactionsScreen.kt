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
    when (val state = viewModel.state) {
        is TransactionsState.Transactions -> {
            TransactionsList(
                navController = navController,
                list = state.list
            )
        }
        is TransactionsState.Error -> {
        }
        TransactionsState.EmptyTransactions -> {
            TransactionsEmpty()
        }
        TransactionsState.Loading -> {
            LoaderScreen()
        }
        TransactionsState.Idle -> {
            viewModel.getTransactions()
            TransactionsIdle()
        }
    }
}

@Composable
fun TransactionsIdle() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "no transactions")
    }
}

@Composable
fun TransactionsEmpty() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.empty_transactions))
    }
}

@Composable
fun TransactionsList(
    navController: NavHostController,
    list: List<Transaction>
) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(list.size) {
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