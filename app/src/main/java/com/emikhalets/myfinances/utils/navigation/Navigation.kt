package com.emikhalets.myfinances.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.ui.screens.lists.ListsScreen
import com.emikhalets.myfinances.ui.screens.new_transaction.NewTransactionScreen
import com.emikhalets.myfinances.ui.screens.new_wallet.NewWalletScreen
import com.emikhalets.myfinances.ui.screens.summary.SummaryScreen
import com.emikhalets.myfinances.ui.screens.transactions.TransactionsScreen
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Transactions
    ) {
        composable(Screens.NewWallet) {
            NewWalletScreen(navController = navController)
        }
        composable(Screens.Transactions) {
            TransactionsScreen(navController = navController)
        }
        composable(Screens.Summary) {
            SummaryScreen(navController = navController)
        }
        composable(Screens.Lists) {
            ListsScreen(navController = navController)
        }
        composable(Screens.NewTransaction) {
            navController.getSerializable<TransactionType>(NavArgs.TRANSACTION_TYPE) {
                NewTransactionScreen(
                    navController = navController,
                    transactionType = it
                )
            }
        }
    }
}