package com.emikhalets.myfinances.ui.screens.new_transaction

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet

data class NewTransactionState(
    val wallets: List<Wallet>,
    val categories: List<Category>,
    val note: String,
    val value: Double,
    val loading: Boolean,
    val saved: Boolean,
    val walletError: Boolean,
    val categoryError: Boolean,
    val valueError: Boolean,
    val error: Exception?
) {

    constructor() : this(
        wallets = emptyList(),
        categories = emptyList(),
        note = "",
        value = 0.0,
        loading = false,
        saved = false,
        walletError = false,
        categoryError = false,
        valueError = false,
        error = null
    )

    fun setLoadedWallets(data: List<Wallet>): NewTransactionState {
        return this.copy(
            wallets = data,
            loading = false
        )
    }

    fun setLoadedCategories(data: List<Category>): NewTransactionState {
        return this.copy(
            categories = data,
            loading = false
        )
    }

    fun setCommonError(exception: Exception): NewTransactionState {
        return this.copy(
            error = exception,
            loading = false
        )
    }

    fun setLoading(): NewTransactionState {
        return this.copy(
            loading = true,
            walletError = false,
            categoryError = false,
            error = null,
        )
    }

    fun setSavedTransaction(): NewTransactionState {
        return this.copy(
            saved = true,
            loading = false
        )
    }

    fun setWalletError(): NewTransactionState {
        return this.copy(walletError = true)
    }

    fun setCategoryError(): NewTransactionState {
        return this.copy(categoryError = true)
    }

    fun errorMessage(): String = error?.message.toString()
}