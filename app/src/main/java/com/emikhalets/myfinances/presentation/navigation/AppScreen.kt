package com.emikhalets.myfinances.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.emikhalets.myfinances.R

enum class AppScreen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    MAIN("main", R.string.app_name_title, Icons.Rounded.Home),
    INCOMES("incomes", R.string.title_incomes_screen, Icons.Default.Home),
    EXPENSES("expenses", R.string.title_expenses_screen, Icons.Default.Home);

    companion object {

        fun getAll(): List<AppScreen> = values().toList()

        fun getRoot(): List<AppScreen> = listOf(MAIN)

        fun AppScreen?.isRootScreen(): Boolean {
            this ?: return false
            val screen = getRoot().find { it == this }
            return screen != null
        }

        @Composable
        fun getScreen(entry: NavBackStackEntry?): AppScreen? {
            return values().find { entry?.destination?.route == it.route }
        }
    }
}