package com.emikhalets.myfinances.ui.screens.first_launch

import com.emikhalets.myfinances.data.entity.Wallet

data class FirstLaunchState(
    val wallets: List<Wallet>?,
    val loading: Boolean,
    val error: Exception?
) {

    constructor() : this(
        wallets = null,
        loading = false,
        error = null
    )

    fun setLoadedWallets(data: List<Wallet>): FirstLaunchState {
        return this.copy(
            wallets = data,
            loading = false
        )
    }

    fun setCommonError(exception: Exception): FirstLaunchState {
        return this.copy(
            error = exception,
            loading = false
        )
    }

    fun setLoading(): FirstLaunchState {
        return this.copy(
            loading = true,
            error = null
        )
    }

    fun errorMessage(): String = error?.message.toString()
}