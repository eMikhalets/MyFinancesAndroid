package com.emikhalets.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.presentation.R

sealed class Screen(val route: String, val title: Int, val icon: ImageVector) {

    object Transactions : Screen(
        "transactions",
        R.string.title_transactions,
        Icons.Rounded.LibraryBooks
    )

    object Categories : Screen(
        "categories",
        R.string.title_categories,
        Icons.Rounded.Category
    )

    object Wallets : Screen(
        "wallets",
        R.string.title_wallets,
        Icons.Rounded.Wallet
    )

    object Settings : Screen(
        "settings",
        R.string.title_settings,
        Icons.Rounded.Settings
    )

//    object CategoryEdit : Screen(
//        "category_edit",
//        R.string.title_category_edit
//    )
//
//    object WalletEdit : Screen(
//        "wallet_edit",
//        R.string.title_wallet_edit
//    )
//
//    object CurrencyEdit : Screen(
//        "currency_edit",
//        R.string.title_currency_edit
//    )
//
//    object TransactionEdit : Screen(
//        "transaction_edit",
//        R.string.title_transaction_edit
//    )
}