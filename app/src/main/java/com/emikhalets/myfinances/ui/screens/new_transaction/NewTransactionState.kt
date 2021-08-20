package com.emikhalets.myfinances.ui.screens.new_transaction

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet

data class NewTransactionState(
    val categories: List<Category> = emptyList(),
    val wallets: List<Wallet> = emptyList(),
    val savedCategory: Boolean = false,
    val savedTransaction: Boolean = false,
    val savedWallet: Boolean = false,
    val error: Exception? = null
) {

    fun errorMessage(): String {
        return error?.message ?: ""
    }
}