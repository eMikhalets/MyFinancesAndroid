package com.emikhalets.myfinances.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.myfinances.presentation.screens.main.MainScreen
import com.emikhalets.myfinances.presentation.screens.transaction.TransactionScreen

private val transactionArgsRoute = "${AppScreen.Transaction.route}/{${AppNavArgs.TRANSACTION_ID}}"
private val categoryArgsRoute = "${AppScreen.Category.route}/{${AppNavArgs.CATEGORY_ID}}"
private val walletArgsRoute = "${AppScreen.Wallet.route}/{${AppNavArgs.WALLET_ID}}"

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {
        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }
        composable(
            transactionArgsRoute,
            listOf(navArgument(AppNavArgs.TRANSACTION_ID) { type = NavType.LongType })
        ) {
            val transactionId = it.arguments?.getLong(AppNavArgs.TRANSACTION_ID) ?: -1L
            TransactionScreen(navController, transactionId)
        }
        composable(AppScreen.Categories.route) {
        }
        composable(
            categoryArgsRoute,
            listOf(navArgument(AppNavArgs.TRANSACTION_ID) { type = NavType.LongType })
        ) {
            val categoryId = it.arguments?.getLong(AppNavArgs.TRANSACTION_ID) ?: -1
        }
        composable(AppScreen.Wallets.route) {
        }
        composable(
            walletArgsRoute,
            listOf(navArgument(AppNavArgs.TRANSACTION_ID) { type = NavType.LongType })
        ) {
            val walletId = it.arguments?.getLong(AppNavArgs.TRANSACTION_ID) ?: -1
        }
    }
}