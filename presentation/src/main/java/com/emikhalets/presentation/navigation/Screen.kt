package com.emikhalets.presentation.navigation

import com.emikhalets.presentation.R

sealed class Screen(val route: String, val title: Int) {

    object Main : Screen(
        "main",
        R.string.title_main
    )

    object Categories : Screen(
        "categories",
        R.string.title_categories
    )

    object CategoryEdit : Screen(
        "category_edit",
        R.string.title_category_edit
    )

    object Wallets : Screen(
        "wallets",
        R.string.title_wallets
    )

    object WalletEdit : Screen(
        "wallet_edit",
        R.string.title_wallet_edit
    )

    object Currencies : Screen(
        "currencies",
        R.string.title_currencies
    )

    object CurrencyEdit : Screen(
        "currency_edit",
        R.string.title_currency_edit
    )

    object Transactions : Screen(
        "transactions",
        R.string.title_transactions
    )

    object TransactionEdit : Screen(
        "transaction_edit",
        R.string.title_transaction_edit
    )
}