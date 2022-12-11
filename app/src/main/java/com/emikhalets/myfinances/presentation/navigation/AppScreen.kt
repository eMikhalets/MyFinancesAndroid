package com.emikhalets.myfinances.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.emikhalets.myfinances.R

enum class AppScreen(val route: String, @StringRes val title: Int) {
    MAIN("main", R.string.app_name_title),
    CATEGORIES("categories", R.string.app_categories),
    TRANSACTION_EDIT("transaction_edit", R.string.title_edit_transaction),
    INCOMES("incomes", R.string.title_incomes_screen),
    EXPENSES("expenses", R.string.title_expenses_screen);

    companion object {

        fun AppScreen?.isRootScreen(): Boolean {
            this ?: return false
            val screen = getRoot().find { it == this }
            return screen != null
        }

        @Composable
        fun getScreen(entry: NavBackStackEntry?): AppScreen? {
            return values().find { entry?.destination?.route == it.route }
        }

        private fun getRoot(): List<AppScreen> = listOf(MAIN)
    }
}