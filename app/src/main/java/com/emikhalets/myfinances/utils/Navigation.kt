package com.emikhalets.myfinances.utils

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.ui.screens.new_transaction.NewTransactionScreen
import com.emikhalets.myfinances.ui.screens.summary.SummaryScreen
import com.emikhalets.myfinances.ui.screens.transactions.TransactionsScreen
import com.emikhalets.myfinances.utils.enums.TransactionType

object Screens {
    const val TRANSACTIONS = "transactions"
    const val SUMMARY = "summary"
    const val NEW_TRANSACTION = "new_transaction"
    const val NewCategory = "new_category"
    const val Categories = "categories"
}

private object Args {
    const val TRANSACTION_TYPE = "arg_transaction_type"
}

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.TRANSACTIONS
    ) {
        composable(Screens.TRANSACTIONS) {
            TransactionsScreen(navController = navController)
        }
        composable(Screens.SUMMARY) {
            SummaryScreen(navController = navController)
        }
        composable(Screens.NEW_TRANSACTION) {
            navController.getSerializable<TransactionType>(Args.TRANSACTION_TYPE) {
                NewTransactionScreen(
                    navController = navController,
                    transactionType = it
                )
            }
        }
    }
}

fun NavHostController.navigateToTransactions() {
    if (this.currentDestination?.route != Screens.TRANSACTIONS) {
        navigate(Screens.TRANSACTIONS)
    }
}

fun NavHostController.navigateToSummary() {
    if (this.currentDestination?.route != Screens.SUMMARY) {
        navigate(Screens.SUMMARY)
    }
}

fun NavHostController.navigateToNewTransaction(type: TransactionType) {
    navigate(Screens.NEW_TRANSACTION, bundleOf(Args.TRANSACTION_TYPE to type))
}

private fun NavController.navigate(
    route: String,
    params: Bundle?,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.currentBackStackEntry?.arguments = params
    navigate(route, builder)
}

@Composable
fun <T> NavController.getSerializable(
    key: String,
    content: @Composable (T) -> Unit
) {
    this.previousBackStackEntry?.arguments?.getSerializable(key)?.let {
        content(it as T)
    }
}