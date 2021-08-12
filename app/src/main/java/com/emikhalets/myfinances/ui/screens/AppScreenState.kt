package com.emikhalets.myfinances.ui.screens

import com.emikhalets.myfinances.data.entity.Wallet

data class AppScreenState(
    val wallets: List<Wallet>,
    val loading: Boolean,
    val error: Exception?
) {

    constructor() : this(
        wallets = emptyList(),
        loading = false,
        error = null
    )

    fun setLoadedWallets(list: List<Wallet>): AppScreenState {
        return this.copy(
            loading = false,
            wallets = list
        )
    }

    fun setCommonError(exception: Exception): AppScreenState {
        return this.copy(
            error = exception,
            loading = false
        )
    }

    fun setLoading(): AppScreenState {
        return this.copy(
            loading = true,
            error = null
        )
    }

    fun errorMessage(): String = error?.message.toString()
}