package com.emikhalets.myfinances.ui.screens.transaction_details

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet

data class TransactionDetailsState(
    val categories: List<Category> = emptyList(),
    val transaction: Transaction? = null,
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