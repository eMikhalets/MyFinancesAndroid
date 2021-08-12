package com.emikhalets.myfinances.utils

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.ui.screens.first_launch.FirstLaunchScreen
import com.emikhalets.myfinances.ui.screens.new_transaction.NewTransactionScreen
import com.emikhalets.myfinances.ui.screens.new_wallet.NewWalletScreen
import com.emikhalets.myfinances.ui.screens.summary.SummaryScreen
import com.emikhalets.myfinances.ui.screens.transactions.TransactionsScreen
import com.emikhalets.myfinances.utils.enums.TransactionType

object Screens {
    const val FirstLaunch = "first_launch"
    const val Transactions = "transactions"
    const val Summary = "summary"
    const val NewTransaction = "new_transaction"
    const val NewCategory = "new_category"
    const val NewWallet = "new_wallet"
    const val Categories = "categories"
}

private object Args {
    const val TRANSACTION_TYPE = "arg_transaction_type"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    selectedWallet: Wallet,
    showToolbar: (Boolean) -> Unit,
    showBottomNav: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screens.FirstLaunch
    ) {
        composable(Screens.FirstLaunch) {
            showToolbar(false)
            showBottomNav(false)
            FirstLaunchScreen(navController = navController)
        }
        composable(Screens.NewWallet) {
            showToolbar(false)
            showBottomNav(false)
            NewWalletScreen(navController = navController)
        }
        composable(Screens.Transactions) {
            showToolbar(true)
            showBottomNav(true)
            TransactionsScreen(navController = navController)
        }
        composable(Screens.Summary) {
            showToolbar(true)
            showBottomNav(true)
            SummaryScreen(navController = navController)
        }
        composable(Screens.NewTransaction) {
            showToolbar(false)
            showBottomNav(false)
            navController.getSerializable<TransactionType>(Args.TRANSACTION_TYPE) {
                NewTransactionScreen(
                    navController = navController,
                    transactionType = it
                )
            }
        }
    }
}

fun NavHostController.navigateToNewWalletAsStart() {
    currentDestination?.route?.let {
        navigate(Screens.NewWallet) {
            popUpTo(it) { inclusive = true }
        }
    }
}

fun NavHostController.navigateToTransactionsAsStart() {
    currentDestination?.route?.let {
        navigate(Screens.Transactions) {
            popUpTo(it) { inclusive = true }
        }
    }
}

fun NavHostController.navigateToTransactions() {
    if (this.currentDestination?.route != Screens.Transactions) {
        navigate(Screens.Transactions)
    }
}

fun NavHostController.navigateToSummary() {
    if (this.currentDestination?.route != Screens.Summary) {
        navigate(Screens.Summary)
    }
}

fun NavHostController.navigateToNewTransaction(type: TransactionType) {
    navigate(Screens.NewTransaction, bundleOf(Args.TRANSACTION_TYPE to type))
}

fun NavHostController.navigateBack() {
    popBackStack()
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