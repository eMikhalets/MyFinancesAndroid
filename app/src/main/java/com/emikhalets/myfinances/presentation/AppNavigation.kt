package com.emikhalets.myfinances.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.screens.categories.CategoriesScreen
import com.emikhalets.myfinances.presentation.screens.main.MainScreen
import com.emikhalets.myfinances.presentation.screens.wallet.WalletScreen
import com.emikhalets.myfinances.presentation.screens.wallets.WalletsScreen

private val categoryArgsRoute = "${AppScreen.Category.route}/{${AppNavArgs.CATEGORY_ID}}"
private val walletArgsRoute = "${AppScreen.Wallet.route}/{${AppNavArgs.WALLET_ID}}"

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {
        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }
        composable(AppScreen.Categories.route) {
            CategoriesScreen(navController)
        }
        composable(
            categoryArgsRoute,
            listOf(navArgument(AppNavArgs.TRANSACTION_ID) { type = NavType.LongType })
        ) {
            val categoryId = it.arguments?.getLong(AppNavArgs.TRANSACTION_ID) ?: -1
        }
        composable(AppScreen.Wallets.route) {
            WalletsScreen(navController)
        }
        composable(
            walletArgsRoute,
            listOf(navArgument(AppNavArgs.TRANSACTION_ID) { type = NavType.LongType })
        ) {
            val walletId = it.arguments?.getLong(AppNavArgs.TRANSACTION_ID) ?: -1
            WalletScreen(navController, walletId)
        }
    }
}

fun NavHostController.navigateToCategories() {
    navigate(AppScreen.Categories.route)
}

fun NavHostController.navigateToCategory(id: Long) {
    navigate("${AppScreen.Category.route}/$id")
}

fun NavHostController.navigateToWallets() {
    navigate(AppScreen.Wallets.route)
}

fun NavHostController.navigateToWallet(id: Long) {
    navigate("${AppScreen.Wallet.route}/$id")
}

object AppNavArgs {
    const val TRANSACTION_ID = "transactionId"
    const val CATEGORY_ID = "categoryId"
    const val WALLET_ID = "walletId"
}

sealed class AppScreen(val route: String, @StringRes val title: Int) {
    object Main : AppScreen("main", R.string.title_main_screen)
    object Categories : AppScreen("categories", R.string.title_category_screen)
    object Category : AppScreen("category", R.string.title_wallets_screen)
    object Wallets : AppScreen("wallets", R.string.title_wallets_screen)
    object Wallet : AppScreen("wallet", R.string.title_wallet_screen)
}