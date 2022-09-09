package com.emikhalets.myfinances.utils.navigation

import androidx.annotation.StringRes
import com.emikhalets.myfinances.R

sealed class AppScreen(val route: String, @StringRes val title: Int) {
    object Main : AppScreen("main", R.string.title_main_screen)
    object Transaction : AppScreen("transaction", R.string.title_categories_screen)
    object Categories : AppScreen("categories", R.string.title_category_screen)
    object Category : AppScreen("category", R.string.title_wallets_screen)
    object Wallets : AppScreen("wallets", R.string.title_wallets_screen)
    object Wallet : AppScreen("wallet", R.string.title_wallet_screen)
}