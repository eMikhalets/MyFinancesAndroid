package com.emikhalets.myfinances.ui.base

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.ui.screens.summary.SummaryScreen
import com.emikhalets.myfinances.ui.screens.transactions.TransactionsScreen

object Screens {
    const val TRANSACTIONS = "transactions"
    const val SUMMARY = "summary"
    const val NewTransaction = "new_transaction"
    const val NewCategory = "new_category"
    const val Categories = "categories"
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