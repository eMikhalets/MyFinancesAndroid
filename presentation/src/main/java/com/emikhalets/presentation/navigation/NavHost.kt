package com.emikhalets.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.presentation.screens.categories.ScreenContent
import com.emikhalets.presentation.screens.category_edit.CategoryEditScreen
import com.emikhalets.presentation.screens.currencies.CurrenciesScreen
import com.emikhalets.presentation.screens.currency_edit.CurrencyEditScreen
import com.emikhalets.presentation.screens.main.MainScreen
import com.emikhalets.presentation.screens.transaction_edit.TransactionEditScreen
import com.emikhalets.presentation.screens.transactions.TransactionsScreen
import com.emikhalets.presentation.screens.wallet_edit.WalletEditScreen
import com.emikhalets.presentation.screens.wallets.WalletsScreen

private const val ARGS_CATEGORY_ID = "ARGS_CATEGORY_ID"
private const val ARGS_CATEGORY_TYPE = "ARGS_CATEGORY_TYPE"
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
                onTransactionEditClick = {},
                onCategoriesClick = { navController.navigate(Screen.Categories.route) },
                onWalletsClick = { navController.navigate(Screen.Wallets.route) },
                onCurrenciesClick = { navController.navigate(Screen.Currencies.route) }
            )
        }
        composable(Screen.Categories.route) {
            ScreenContent(
                onCategoryClick = { categoryId, type ->
                    navController.navigate("${Screen.CategoryEdit.route}/$categoryId/$type")
                },
                onAddCategoryClick = { type ->
                    navController.navigate("${Screen.CategoryEdit.route}/${null}/$type")
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Screen.CategoryEdit.route}/{$ARGS_CATEGORY_ID}/{$ARGS_CATEGORY_TYPE}",
            arguments = listOf(
                navArgument(ARGS_CATEGORY_ID) { type = NavType.LongType },
                navArgument(ARGS_CATEGORY_TYPE) {
                    type = NavType.EnumType(TransactionType::class.java)
                },
            )
        ) {
            CategoryEditScreen(
                categoryId = it.arguments?.getLong(ARGS_CATEGORY_ID),
                type = it.arguments?.getParcelable(ARGS_CATEGORY_ID, TransactionType::class.java),
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Transactions.route) {
            TransactionsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.TransactionEdit.route) {
            TransactionEditScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Wallets.route) {
            WalletsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.WalletEdit.route) {
            WalletEditScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Currencies.route) {
            CurrenciesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.CurrencyEdit.route) {
            CurrencyEditScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}