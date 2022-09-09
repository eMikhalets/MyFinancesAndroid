package com.emikhalets.myfinances.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.presentation.screens.main.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {
        composable(AppScreen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(AppScreen.Transaction.route) {
//            SummaryScreen(navController = navController)
        }
        composable(AppScreen.Categories.route) {
//            ListsScreen(navController = navController)
        }
        composable(AppScreen.Category.route) {
//            navController.getSerializable<TransactionType>(NavArgs.TRANSACTION_TYPE) {
//                NewTransactionScreen(
//                    navController = navController,
//                    transactionType = it
//                )
//            }
        }
        composable(AppScreen.Wallets.route) {
//            navController.getLong(NavArgs.TRANSACTION_ID) {
//                TransactionDetailsScreen(
//                    navController = navController,
//                    transactionId = it
//                )
//            }
        }
        composable(AppScreen.Wallet.route) {
//            navController.getLong(NavArgs.TRANSACTION_ID) {
//                TransactionDetailsScreen(
//                    navController = navController,
//                    transactionId = it
//                )
//            }
        }
    }
}