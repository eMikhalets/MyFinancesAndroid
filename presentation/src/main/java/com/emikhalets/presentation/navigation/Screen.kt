package com.emikhalets.presentation.navigation

sealed class Screen(val route: String, val title: Int) {

    object Main : Screen(
        "main",0
//        R.string.title_main
    )

    object Categories : Screen(
        "categories",0
//        R.string.title_categories
    )

    object CategoryEdit : Screen(
        "category_edit",0
//        R.string.title_category_edit
    )

    object Wallets : Screen(
        "wallets",0
//        R.string.title_wallets
    )

    object WalletEdit : Screen(
        "wallet_edit",0
//        R.string.title_wallet_edit
    )

    object Currencies : Screen(
        "currencies",0
//        R.string.title_currencies
    )

    object CurrencyEdit : Screen(
        "currency_edit",0
//        R.string.title_currency_edit
    )

    object Transactions : Screen(
        "transactions",0
//        R.string.title_transactions
    )

    object TransactionEdit : Screen(
        "transaction_edit",0
//        R.string.title_transaction_edit
    )
}