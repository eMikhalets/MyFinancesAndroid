package com.emikhalets.myfinances.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.R
import com.emikhalets.myfinances.presentation.screens.categories.CategoriesScreen
import com.emikhalets.myfinances.presentation.screens.main.MainScreen
import com.emikhalets.myfinances.presentation.screens.wallets.WalletsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {
        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }
        composable(AppScreen.Categories.route) {
            CategoriesScreen(navController)
        }
        composable(AppScreen.Wallets.route) {
            WalletsScreen(navController)
        }
    }
}

fun NavHostController.navigateToCategories() {
    navigate(AppScreen.Categories.route)
}

fun NavHostController.navigateToWallets() {
    navigate(AppScreen.Wallets.route)
}

sealed class AppScreen(val route: String, @StringRes val title: Int) {
    object Main : AppScreen("main", R.string.title_main_screen)
    object Categories : AppScreen("categories", R.string.title_category_screen)
    object Wallets : AppScreen("wallets", R.string.title_wallets_screen)
}