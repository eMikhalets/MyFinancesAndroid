package com.emikhalets.myfinances.ui.screens.transaction_details

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet

data class TransactionDetailsState(
    val categories: List<Category>,
    val transaction: Transaction?,
    val wallets: List<Wallet>,
    val savedCategory: Boolean,
    val savedTransaction: Boolean,
    val savedWallet: Boolean,
    val error: Exception?
) {

    constructor() : this(
        categories = emptyList(),
        transaction = null,
        wallets = emptyList(),
        savedCategory = false,
        savedWallet = false,
        savedTransaction = false,
        error = null
    )

    fun setLoadedCategories(data: List<Category>): TransactionDetailsState {
        return this.copy(
            categories = data,
            savedCategory = false
        )
    }

    fun setLoadedTransaction(item: Transaction): TransactionDetailsState {
        return this.copy(
            transaction = item,
            savedTransaction = false
        )
    }

    fun setLoadedWallets(data: List<Wallet>): TransactionDetailsState {
        return this.copy(
            wallets = data,
            savedWallet = false
        )
    }

    fun setSavedCategory(): TransactionDetailsState {
        return this.copy(savedCategory = true)
    }

    fun setSavedTransaction(): TransactionDetailsState {
        return this.copy(savedTransaction = true)
    }

    fun setSavedWallet(): TransactionDetailsState {
        return this.copy(savedWallet = true)
    }

    fun setCommonError(exception: Exception): TransactionDetailsState {
        return this.copy(error = exception)
    }
}