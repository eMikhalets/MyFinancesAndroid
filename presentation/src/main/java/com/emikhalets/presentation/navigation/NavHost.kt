package com.emikhalets.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.presentation.screens.categories.CategoriesScreen
import com.emikhalets.myfinances.presentation.screens.main.MainScreen

private const val ARGS_CATEGORY_ID = "ARGS_CATEGORY_ID"
private const val ARGS_WALLET_ID = "ARGS_WALLET_ID"
private const val ARGS_CURRENCY_ID = "ARGS_CURRENCY_ID"
private const val ARGS_TRANSACTION_ID = "ARGS_TRANSACTION_ID"
private const val ARGS_TRANSACTION_TYPE = "ARGS_TRANSACTION_TYPE"

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.CategoryEdit.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.Transactions.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.TransactionEdit.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.Wallets.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.WalletEdit.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.Currencies.route) {
            CategoriesScreen(navController)
        }
        composable(Screen.CurrencyEdit.route) {
            CategoriesScreen(navController)
        }
//        composable(
//            route = "transaction_edit/{$ARG_TRANSACTION_ID}/{$ARG_TRANSACTION_TYPE}",
//            arguments = listOf(
//                navArgument(ARG_TRANSACTION_ID) { type = NavType.LongType },
//                navArgument(ARG_TRANSACTION_TYPE) {
//                    type = NavType.EnumType(TransactionType::class.java)
//                }
//            )
//        ) {
//            val id = it.arguments?.getLong(ARG_TRANSACTION_ID)
//            val type = it.arguments
//                ?.getParcelable(ARG_TRANSACTION_TYPE, TransactionType::class.java)
//            TransactionEditScreen(
//                navController = navController,
//                transactionId = id,
//                transactionType = type
//            )
//        }
    }
}