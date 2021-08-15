package com.emikhalets.myfinances.ui.screens.new_transaction

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet

data class NewTransactionState(
    val categories: List<Category>,
    val wallets: List<Wallet>,
    val savedCategory: Boolean,
    val savedTransaction: Boolean,
    val savedWallet: Boolean,
    val error: Exception?
) {

    constructor() : this(
        categories = emptyList(),
        wallets = emptyList(),
        savedCategory = false,
        savedTransaction = false,
        savedWallet = false,
        error = null
    )

    fun setLoadedCategories(data: List<Category>): NewTransactionState {
        return this.copy(
            categories = data,
            savedCategory = false
        )
    }

    fun setLoadedWallets(data: List<Wallet>): NewTransactionState {
        return this.copy(
            wallets = data,
            savedWallet = false
        )
    }

    fun setCommonError(exception: Exception): NewTransactionState {
        return this.copy(error = exception)
    }

    fun setSavedCategory(): NewTransactionState {
        return this.copy(savedCategory = true)
    }

    fun setSavedTransaction(): NewTransactionState {
        return this.copy(savedTransaction = true)
    }

    fun setSavedWallet(): NewTransactionState {
        return this.copy(savedWallet = true)
    }
}