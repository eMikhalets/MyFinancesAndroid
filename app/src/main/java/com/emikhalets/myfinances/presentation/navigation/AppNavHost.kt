package com.emikhalets.myfinances.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.myfinances.presentation.screens.categories.CategoriesScreen
import com.emikhalets.myfinances.presentation.screens.main.MainScreen
import com.emikhalets.myfinances.presentation.screens.transaction_edit.TransactionEditScreen
import com.emikhalets.myfinances.presentation.screens.transactions.ExpensesScreen
import com.emikhalets.myfinances.presentation.screens.transactions.IncomesScreen
import com.emikhalets.myfinances.utils.enums.TransactionType

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.MAIN.route) {

        composable(AppScreen.MAIN.route) {
            MainScreen(navController)
        }

        composable(AppScreen.CATEGORIES.route) {
            CategoriesScreen(navController)
        }

        composable(
            route = AppScreen.TRANSACTION_EDIT.route,
            arguments = listOf(
                navArgument(ARG_TRANSACTION_ID) { type = NavType.LongType },
                navArgument(ARG_TRANSACTION_TYPE) {
                    type = NavType.EnumType(TransactionType::class.java)
                }
            )
        ) {
            val id = it.arguments?.getLong(ARG_TRANSACTION_ID)
            val type = it.arguments
                ?.getParcelable(ARG_TRANSACTION_TYPE, TransactionType::class.java)
            TransactionEditScreen(
                navController = navController,
                transactionId = id,
                transactionType = type
            )
        }

        composable(AppScreen.INCOMES.route) {
            IncomesScreen(navController)
        }

        composable(AppScreen.EXPENSES.route) {
            ExpensesScreen(navController)
        }

    }
}