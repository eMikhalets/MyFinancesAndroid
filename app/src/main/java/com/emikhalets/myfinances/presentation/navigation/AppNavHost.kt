package com.emikhalets.myfinances.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.myfinances.presentation.screens.categories.CategoriesScreen
import com.emikhalets.myfinances.presentation.screens.main.MainScreen
import com.emikhalets.myfinances.presentation.screens.transactions.ExpensesScreen
import com.emikhalets.myfinances.presentation.screens.transactions.IncomesScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.MAIN.route) {

        composable(AppScreen.MAIN.route) {
            MainScreen(navController)
        }

        composable(AppScreen.CATEGORIES.route) {
            CategoriesScreen(navController)
        }

        composable(AppScreen.INCOMES.route) {
            IncomesScreen(navController)
        }

        composable(AppScreen.EXPENSES.route) {
            ExpensesScreen(navController)
        }

    }
}