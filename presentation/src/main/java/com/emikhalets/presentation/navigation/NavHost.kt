package com.emikhalets.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.presentation.screens.categories.CategoriesScreen
import com.emikhalets.presentation.screens.category_edit.CategoryEditScreen
import com.emikhalets.presentation.screens.currencies.CurrenciesScreen
import com.emikhalets.presentation.screens.currency_edit.CurrencyEditScreen
import com.emikhalets.presentation.screens.main.MainScreen
import com.emikhalets.presentation.screens.transaction_edit.TransactionEditScreen
import com.emikhalets.presentation.screens.transactions.TransactionsScreen
import com.emikhalets.presentation.screens.wallet_edit.WalletEditScreen
import com.emikhalets.presentation.screens.wallets.WalletsScreen

private const val ARGS_CATEGORY_ID = "ARGS_CATEGORY_ID"
private const val ARGS_WALLET_ID = "ARGS_WALLET_ID"
private const val ARGS_CURRENCY_ID = "ARGS_CURRENCY_ID"
private const val ARGS_TRANSACTION_ID = "ARGS_TRANSACTION_ID"
private const val ARGS_TRANSACTION_TYPE = "ARGS_TRANSACTION_TYPE"

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                onTransactionsClick = {},
                onCategoriesClick = {},
                onWalletsClick = {},
                onCurrenciesClick = {}
            )
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(
                onBackClick = {}
            )
        }
        composable(Screen.CategoryEdit.route) {
            CategoryEditScreen(
                onBackClick = {}
            )
        }
        composable(Screen.Transactions.route) {
            TransactionsScreen(
                onBackClick = {}
            )
        }
        composable(Screen.TransactionEdit.route) {
            TransactionEditScreen(
                onBackClick = {}
            )
        }
        composable(Screen.Wallets.route) {
            WalletsScreen(
                onBackClick = {}
            )
        }
        composable(Screen.WalletEdit.route) {
            WalletEditScreen(
                onBackClick = {}
            )
        }
        composable(Screen.Currencies.route) {
            CurrenciesScreen(
                onBackClick = {}
            )
        }
        composable(Screen.CurrencyEdit.route) {
            CurrencyEditScreen(
                onBackClick = {}
            )
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