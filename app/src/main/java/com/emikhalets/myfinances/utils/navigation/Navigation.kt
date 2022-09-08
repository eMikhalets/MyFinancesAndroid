package com.emikhalets.myfinances.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.presentation.screens.lists.ListsScreen
import com.emikhalets.myfinances.presentation.screens.new_transaction.NewTransactionScreen
import com.emikhalets.myfinances.presentation.screens.summary.SummaryScreen
import com.emikhalets.myfinances.presentation.screens.transaction_details.TransactionDetailsScreen
import com.emikhalets.myfinances.presentation.screens.transactions.TransactionsScreen
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Transactions.route
    ) {
        composable(Screen.Transactions.route) {
            TransactionsScreen(navController = navController)
        }
        composable(Screen.Summary.route) {
            SummaryScreen(navController = navController)
        }
        composable(Screen.Lists.route) {
            ListsScreen(navController = navController)
        }
        composable(Screen.NewTransaction.route) {
            navController.getSerializable<TransactionType>(NavArgs.TRANSACTION_TYPE) {
                NewTransactionScreen(
                    navController = navController,
                    transactionType = it
                )
            }
        }
        composable(Screen.TransactionDetails.route) {
            navController.getLong(NavArgs.TRANSACTION_ID) {
                TransactionDetailsScreen(
                    navController = navController,
                    transactionId = it
                )
            }
        }
    }
}