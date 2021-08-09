package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emikhalets.myfinances.ui.base.AppScaffold
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.navigateToNewTransaction

@Composable
fun TransactionsScreen(
    navController: NavHostController
) {
    AppScaffold(
        navController = navController,
        showTopBar = true,
        showBottomBar = true
    ) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.fillMaxSize().weight(1f)) {
            }
            AddTransaction(
                navController = navController,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
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