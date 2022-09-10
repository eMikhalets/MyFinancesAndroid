package com.emikhalets.myfinances.utils.navigation

import androidx.navigation.NavHostController

object AppNavArgs {
    const val TRANSACTION_ID = "transactionId"
    const val CATEGORY_ID = "categoryId"
    const val WALLET_ID = "walletId"
}

fun NavHostController.navigateToTransaction(id: Long) {
    navigate("${AppScreen.Transaction.route}/$id")
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